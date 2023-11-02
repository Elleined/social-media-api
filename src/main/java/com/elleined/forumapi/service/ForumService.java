package com.elleined.forumapi.service;

import com.elleined.forumapi.dto.CommentDTO;
import com.elleined.forumapi.dto.PostDTO;
import com.elleined.forumapi.dto.ReplyDTO;
import com.elleined.forumapi.dto.UserDTO;
import com.elleined.forumapi.exception.*;
import com.elleined.forumapi.mapper.CommentMapper;
import com.elleined.forumapi.mapper.PostMapper;
import com.elleined.forumapi.mapper.ReplyMapper;
import com.elleined.forumapi.mapper.UserMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.CommentLike;
import com.elleined.forumapi.model.like.PostLike;
import com.elleined.forumapi.model.like.ReplyLike;
import com.elleined.forumapi.model.mention.CommentMention;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.model.mention.ReplyMention;
import com.elleined.forumapi.service.image.ImageUploader;
import com.elleined.forumapi.service.like.LikeService;
import com.elleined.forumapi.service.mention.MentionService;
import com.elleined.forumapi.utils.DirectoryFolders;
import com.elleined.forumapi.validator.StringValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ForumService {
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final ReplyService replyService;
    private final BlockService blockService;
    private final ModalTrackerService modalTrackerService;
    private final WSNotificationService wsNotificationService;
    private final WSService wsService;

    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final ReplyMapper replyMapper;
    private final UserMapper userMapper;

    private final ImageUploader imageUploader;

    private final LikeService<Post> postLikeService;
    private final LikeService<Comment> commentLikeService;
    private final LikeService<Reply> replyLikeService;

    private final MentionService<PostMention, Post> postMentionService;
    private final MentionService<CommentMention, Comment> commentMentionService;
    private final MentionService<ReplyMention, Reply> replyMentionService;

    @Value("${cropTrade.img.directory}")
    private String cropTradeImgDirectory;


    public List<ReplyDTO> getAllByComment(User currentUser, Comment comment) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);
        if (comment.isDeleted()) throw new ResourceNotFoundException("Comment with id of " + commentId + " might already been deleted or does not exists anymore!");

        replyService.readAllReplies(currentUser, comment);
        likeService.readLikes(currentUser, comment);
        mentionService.readMentions(currentUser, comment);

        modalTrackerService.saveTrackerOfUserById(currentUserId, commentId, "REPLY");
        return replyService.getAllByComment(currentUser, comment).stream()
                .map(replyMapper::toDTO)
                .toList();
    }

    public CommentDTO updateUpvote(User currentUser, Comment comment)
            throws ResourceNotFoundException,
            UpvoteException {

        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);

        if (comment.isDeleted()) throw new ResourceNotFoundException("The comment you trying to upvote might be deleted by the author or does not exists anymore!");
        if (currentUser.isAlreadyUpvoted(comment)) throw new UpvoteException("You can only up vote and down vote a comment once!");

        commentService.updateUpvote(currentUser, comment);
        return commentMapper.toDTO(comment);
    }

    public PostDTO updateCommentSectionStatus(User currentUser, Post post)
            throws ResourceNotFoundException,
            NotOwnedException {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        if (currentUser.notOwned(post)) throw new NotOwnedException("User with id of " + currentUserId + " doesn't have post with id of " + postId);

        postService.updateCommentSectionStatus(post);
        return postMapper.toDTO(post);
    }

    public PostDTO updatePostBody(User currentUser, Post post, String newBody)
            throws ResourceNotFoundException,
            NotOwnedException {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        if (post.getBody().equals(newBody)) return postMapper.toDTO(post);
        if (currentUser.notOwned(post)) throw new NotOwnedException("User with id of " + currentUserId + " doesn't have post with id of " + postId);

        postService.updateBody(post, newBody);
        return postMapper.toDTO(post);
    }

    public CommentDTO updateCommentBody(User currentUser, Post post, Comment comment, String newBody)
            throws ResourceNotFoundException,
            NotOwnedException {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        if (post.doesNotHave(comment)) throw new ResourceNotFoundException("Comment with id of " + commentId + " are not associated with post with id of " + postId);
        if (comment.getBody().equals(newBody)) return commentMapper.toDTO(comment);
        if (currentUser.notOwned(comment)) throw new NotOwnedException("User with id of " + currentUserId + " doesn't have comment with id of " + commentId);

        commentService.updateBody(comment, newBody);
        CommentDTO commentDTO = commentMapper.toDTO(comment);
        wsService.broadcastComment(commentDTO);
        return commentDTO;
    }

    public ReplyDTO updateReplyBody(User currentUser, Reply reply, String newReplyBody)
            throws ResourceNotFoundException,
            NotOwnedException {

        User currentUser = userService.getById(currentUserId);
        Reply reply = replyService.getById(replyId);

        if (reply.getBody().equals(newReplyBody)) return replyMapper.toDTO(reply);
        if (currentUser.notOwned(reply)) throw new NotOwnedException("User with id of " + currentUserId + " doesn't have reply with id of " + replyId);

        replyService.updateReplyBody(reply, newReplyBody);
        ReplyDTO replyDTO = replyMapper.toDTO(reply);
        wsService.broadcastReply(replyDTO);
        return replyDTO;
    }


    public List<UserDTO> getSuggestedMentions(User currentUser, String name) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        return userService.getSuggestedMentions(currentUser, name)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public void blockUser(User currentUser, int userToBeBlockedId)
            throws ResourceNotFoundException,
            BlockedException {

        if (currentUserId == userToBeBlockedId) throw new BlockedException("You cannot blocked yourself!");
        User currentUser = userService.getById(currentUserId);
        User userToBeBlocked = userService.getById(userToBeBlockedId);
        blockService.blockUser(currentUser, userToBeBlocked);
    }

    public void unBlockUser(User currentUser, int userToBeUnblockedId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        User userToBeUnblocked = userService.getById(userToBeUnblockedId);
        blockService.unBlockUser(currentUser, userToBeUnblocked);
    }

    public boolean isBlockedBy(User currentUser, int userToCheckId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        User userToCheck = userService.getById(userToCheckId);
        return blockService.isBlockedBy(currentUser, userToCheck);
    }

    public boolean isYouBeenBlockedBy(User currentUser, int suspectedUserId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        User suspectedUser = userService.getById(suspectedUserId);
        return blockService.isYouBeenBlockedBy(currentUser, suspectedUser);
    }

    public Set<UserDTO> getAllBlockedUsers(User currentUser) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        return blockService.getAllBlockedUsers(currentUser)
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toSet());
    }

    public PostDTO likePost(User respondent, Post post)
            throws ResourceNotFoundException, BlockedException {

        User currentUser = userService.getById(respondentId);
        Post post = postService.getById(postId);

        if (post.isDeleted()) throw new ResourceNotFoundException("Cannot like/unlike! The post with id of " + post.getId() + " you are trying to like/unlike might already been deleted or does not exists!");
        if (blockService.isBlockedBy(currentUser, post.getAuthor())) throw new BlockedException("Cannot like/unlike! You blocked the author of this post with id of !" + post.getAuthor().getId());
        if (blockService.isYouBeenBlockedBy(currentUser, post.getAuthor())) throw  new BlockedException("Cannot like/unlike! The author of this post with id of " + post.getAuthor().getId() + " already blocked you");

        if (likeService.isUserAlreadyLiked(currentUser, post)) {
            likeService.unlike(currentUser, post);
            return postMapper.toDTO(post);
        }

        PostLike postLike = likeService.like(currentUser, post);
        wsNotificationService.broadcastLike(postLike);
        return postMapper.toDTO(post);
    }

    public CommentDTO likeComment(User respondent, Post post, Comment comment)
            throws ResourceNotFoundException, BlockedException {

        User currentUser = userService.getById(respondentId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        if (post.doesNotHave(comment)) throw new ResourceNotFoundException("Post with id of " + postId + " doesn't have comment with id of " + commentId);
        if (comment.isDeleted()) throw new ResourceNotFoundException("Cannot like/unlike! The comment with id of " + comment.getId() + " you are trying to like/unlike might already been deleted or does not exists!");
        if (blockService.isBlockedBy(currentUser, comment.getCommenter())) throw new BlockedException("Cannot like/unlike! You blocked the author of this comment with id of !" + comment.getCommenter().getId());
        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter())) throw new BlockedException("Cannot like/unlike! The author of this comment with id of " + comment.getCommenter().getId() + " already blocked you");

        if (likeService.isUserAlreadyLiked(currentUser, comment)) {
            likeService.unlike(currentUser, comment);
            return commentMapper.toDTO(comment);
        }

        CommentLike commentLike = likeService.like(currentUser, comment);
        wsNotificationService.broadcastLike(commentLike);
        return commentMapper.toDTO(comment);
    }

    public ReplyDTO likeReply(User respondent, Reply reply)
            throws ResourceNotFoundException, BlockedException {

        if (reply.isDeleted()) throw new ResourceNotFoundException("Cannot like/unlike! The reply with id of " + reply.getId() + " you are trying to like/unlike might already be deleted or does not exists!");
        if (blockService.isBlockedBy(currentUser, reply.getReplier())) throw new BlockedException("Cannot like/unlike! You blocked the author of this reply with id of !" + reply.getReplier().getId());
        if (blockService.isYouBeenBlockedBy(currentUser, reply.getReplier())) throw  new BlockedException("Cannot like/unlike! The author of this reply with id of " + reply.getReplier().getId() + " already blocked you");

        if (likeService.isUserAlreadyLiked(currentUser, reply)) {
            likeService.unlike(currentUser, reply);
            return replyMapper.toDTO(reply);
        }

        ReplyLike replyLike = likeService.like(currentUser, reply);
        wsNotificationService.broadcastLike(replyLike);
        return replyMapper.toDTO(reply);
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = User.builder()
                .picture(userDTO.picture())
                .name(userDTO.name())
                .email(userDTO.email())
                .UUID(userDTO.UUID())
                .build();

        userService.save(user);
        return userMapper.toDTO(user);
    }
}
