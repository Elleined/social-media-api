package com.forum.application.service;

import com.forum.application.dto.CommentDTO;
import com.forum.application.dto.PostDTO;
import com.forum.application.dto.ReplyDTO;
import com.forum.application.dto.UserDTO;
import com.forum.application.exception.*;
import com.forum.application.mapper.CommentMapper;
import com.forum.application.mapper.PostMapper;
import com.forum.application.mapper.ReplyMapper;
import com.forum.application.mapper.UserMapper;
import com.forum.application.model.Comment;
import com.forum.application.model.Post;
import com.forum.application.model.Reply;
import com.forum.application.model.User;
import com.forum.application.validator.StringValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final LikeService likeService;
    private final MentionService mentionService;
    private final ModalTrackerService modalTrackerService;

    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final ReplyMapper replyMapper;
    private final UserMapper userMapper;

    public PostDTO savePost(int currentUserId, String body, String attachedPicture, Set<Integer> mentionedUserIds)
            throws EmptyBodyException, BlockedException, ResourceNotFoundException {

        User currentUser = userService.getById(currentUserId);
        if (StringValidator.isNotValidBody(body)) throw new EmptyBodyException("Body cannot be empty! Please provide text for your post to be posted!");

        Post post = postService.save(currentUser, body, attachedPicture);
        if (mentionedUserIds != null)
            mentionedUserIds.forEach(mentionedUserId -> mentionService.addMention(currentUser, mentionedUserId, post));
        return postMapper.toDTO(post);
    }

    public CommentDTO saveComment(int currentUserId, int postId, String body, String attachedPicture, Set<Integer> mentionedUserIds)
            throws ResourceNotFoundException,
            ClosedCommentSectionException,
            BlockedException,
            EmptyBodyException {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        if (StringValidator.isNotValidBody(body)) throw new EmptyBodyException("Comment body cannot be empty! Please provide text for your comment");
        if (postService.isCommentSectionClosed(post)) throw new ClosedCommentSectionException("Cannot comment because author already closed the comment section for this post!");
        if (postService.isDeleted(post)) throw new ResourceNotFoundException("The post you trying to comment is either be deleted or does not exists anymore!");
        if (blockService.isBlockedBy(currentUser, post.getAuthor())) throw new BlockedException("Cannot comment because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, post.getAuthor())) throw new BlockedException("Cannot comment because this user block you already!");

        Comment comment = commentService.save(currentUser, post, body, attachedPicture);
        if (mentionedUserIds != null)
            mentionedUserIds.forEach(mentionedUserId -> mentionService.addMention(currentUser, mentionedUserId, comment));
        return commentMapper.toDTO(comment);
    }

    public ReplyDTO saveReply(int currentUserId, int commentId, String body, String attachedPicture, Set<Integer> mentionedUserIds)
            throws EmptyBodyException,
            ClosedCommentSectionException,
            ResourceNotFoundException,
            BlockedException {

        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);

        if (StringValidator.isNotValidBody(body)) throw new EmptyBodyException("Reply body cannot be empty!");
        if (commentService.isCommentSectionClosed(comment)) throw new ClosedCommentSectionException("Cannot reply to this comment because author already closed the comment section for this post!");
        if (commentService.isDeleted(comment)) throw new ResourceNotFoundException("The comment you trying to reply is either be deleted or does not exists anymore!");
        if (blockService.isBlockedBy(currentUser, comment.getCommenter())) throw new BlockedException("Cannot reply because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter())) throw new BlockedException("Cannot reply because this user block you already!");

        Reply reply = replyService.save(currentUser, comment, body, attachedPicture);
        if (mentionedUserIds != null)
            mentionedUserIds.forEach(mentionedUserId -> mentionService.addMention(currentUser, mentionedUserId, reply));

        return replyMapper.toDTO(reply);
    }


    public void deletePost(int currentUserId, int postId)
            throws ResourceNotFoundException,
            NotOwnedException {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        
        if (postService.isUserNotOwnedPost(currentUser, post)) throw new NotOwnedException("User with id of " + currentUserId + " doesn't have post with id of " + postId);
        postService.delete(post);
    }

    public CommentDTO deleteComment(int currentUserId, int commentId)
            throws ResourceNotFoundException,
            NotOwnedException {

        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);
        if (commentService.isUserNotOwnedComment(currentUser, comment)) throw new NotOwnedException("User with id of " + currentUserId + " doesn't have comment with id of " + commentId);

        commentService.delete(comment);
        return commentMapper.toDTO(comment);
    }

    public ReplyDTO deleteReply(int currentUserId, int replyId)
            throws ResourceNotFoundException,
            NotOwnedException {

        User currentUser = userService.getById(currentUserId);
        Reply reply = replyService.getById(replyId);
        if (replyService.isUserNotOwnedReply(currentUser, reply)) throw new NotOwnedException("User with id of " + currentUserId + " doesn't have reply with id of " + replyId);

        replyService.delete(reply);
        return replyMapper.toDTO(reply);
    }

    public List<PostDTO> getAllByAuthorId(int authorId) throws ResourceNotFoundException {
        User author = userService.getById(authorId);
        return postService.getAllByAuthorId(author)
                .stream()
                .map(postMapper::toDTO)
                .toList();
    }

    public List<PostDTO> getAllPost(int currentUserId) throws ResourceNotFoundException {
        modalTrackerService.saveTrackerOfUserById(currentUserId, 0, "POST");
        User currentUser = userService.getById(currentUserId);
        likeService.readLikes(currentUser);
        mentionService.readMentions(currentUser);
        return postService.getAll(currentUser)
                .stream()
                .map(postMapper::toDTO)
                .toList();
    }

    public List<CommentDTO> getAllByPost(int currentUserId, int postId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        if (postService.isDeleted(post)) throw new ResourceNotFoundException("Post with id of " + postId + " might already been deleted or does not exists anymore!");

        commentService.readAllComments(currentUser, post);
        likeService.readLikes(currentUser, post);
        mentionService.readMentions(currentUser, post);

        modalTrackerService.saveTrackerOfUserById(currentUserId, postId, "COMMENT");
        return commentService.getAllByPost(currentUser, post).stream()
                .map(commentMapper::toDTO)
                .toList();
    }

    public List<ReplyDTO> getAllByComment(int currentUserId, int commentId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);

        if (commentService.isDeleted(comment)) throw new ResourceNotFoundException("Comment with id of " + commentId + " might already been deleted or does not exists anymore!");

        replyService.readAllReplies(currentUser, comment);
        likeService.readLikes(currentUser, comment);
        mentionService.readMentions(currentUser, comment);
        modalTrackerService.saveTrackerOfUserById(currentUserId, commentId, "REPLY");
        return replyService.getAllByComment(currentUser, comment).stream()
                .map(replyMapper::toDTO)
                .toList();
    }

    public CommentDTO updateUpvote(int currentUserId, int commentId)
            throws ResourceNotFoundException,
            UpvoteException {

        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);

        if (commentService.isDeleted(comment)) throw new ResourceNotFoundException("The comment you trying to upvote might be deleted by the author or does not exists anymore!");
        if (commentService.isUserAlreadyUpvoteComment(currentUser, commentId)) throw new UpvoteException("You can only up vote and down vote a comment once!");

        commentService.updateUpvote(currentUser, comment);
        return commentMapper.toDTO(comment);
    }

    public PostDTO updateCommentSectionStatus(int currentUserId, int postId)
            throws ResourceNotFoundException,
            NotOwnedException {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        if (postService.isUserNotOwnedPost(currentUser, post)) throw new NotOwnedException("User with id of " + currentUserId + " doesn't have post with id of " + postId);

        postService.updateCommentSectionStatus(post);
        return postMapper.toDTO(post);
    }

    public PostDTO updatePostBody(int currentUserId, int postId, String newBody)
            throws ResourceNotFoundException,
            NotOwnedException {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        if (post.getBody().equals(newBody)) return postMapper.toDTO(post);
        if (postService.isUserNotOwnedPost(currentUser, post)) throw new NotOwnedException("User with id of " + currentUserId + " doesn't have post with id of " + postId);

        postService.updateBody(post, newBody);
        return postMapper.toDTO(post);
    }

    public CommentDTO updateCommentBody(int currentUserId, int commentId, String newBody)
            throws ResourceNotFoundException,
            NotOwnedException {

        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);
        if (comment.getBody().equals(newBody)) return commentMapper.toDTO(comment);
        if (commentService.isUserNotOwnedComment(currentUser, comment)) throw new NotOwnedException("User with id of " + currentUserId + " doesn't have comment with id of " + commentId);

        commentService.updateBody(comment, newBody);
        return commentMapper.toDTO(comment);
    }

    public ReplyDTO updateReplyBody(int currentUserId, int replyId, String newReplyBody)
            throws ResourceNotFoundException,
            NotOwnedException {

        User currentUser = userService.getById(currentUserId);
        Reply reply = replyService.getById(replyId);

        if (reply.getBody().equals(newReplyBody)) return replyMapper.toDTO(reply);
        if (replyService.isUserNotOwnedReply(currentUser, reply)) throw new NotOwnedException("User with id of " + currentUserId + " doesn't have reply with id of " + replyId);

        replyService.updateReplyBody(reply, newReplyBody);
        return replyMapper.toDTO(reply);
    }

    public List<UserDTO> getAllUser(int currentUserId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        return userService.getAllUser(currentUser)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public List<UserDTO> getSuggestedMentions(int currentUserId, String name) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        return userService.getSuggestedMentions(currentUser, name)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public void blockUser(int currentUserId, int userToBeBlockedId)
            throws ResourceNotFoundException,
            BlockedException {

        if (currentUserId == userToBeBlockedId) throw new BlockedException("You cannot blocked yourself!");
        User currentUser = userService.getById(currentUserId);
        User userToBeBlocked = userService.getById(userToBeBlockedId);
        blockService.blockUser(currentUser, userToBeBlocked);
    }

    public void unBlockUser(int currentUserId, int userToBeUnblockedId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        User userToBeUnblocked = userService.getById(userToBeUnblockedId);
        blockService.unBlockUser(currentUser, userToBeUnblocked);
    }

    public boolean isBlockedBy(int currentUserId, int userToCheckId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        User userToCheck = userService.getById(userToCheckId);
        return blockService.isBlockedBy(currentUser, userToCheck);
    }

    public boolean isYouBeenBlockedBy(int currentUserId, int suspectedUserId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        User suspectedUser = userService.getById(suspectedUserId);
        return blockService.isYouBeenBlockedBy(currentUser, suspectedUser);
    }

    public Set<UserDTO> getAllBlockedUsers(int currentUserId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        return blockService.getAllBlockedUsers(currentUser)
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toSet());
    }

    public PostDTO likePost(int respondentId, int postId)
            throws ResourceNotFoundException, BlockedException {

        User currentUser = userService.getById(respondentId);
        Post post = postService.getById(postId);

        if (postService.isDeleted(post)) throw new ResourceNotFoundException("Cannot like/unlike! The post with id of " + post.getId() + " you are trying to like/unlike might already been deleted or does not exists!");
        if (blockService.isBlockedBy(currentUser, post.getAuthor())) throw new BlockedException("Cannot like/unlike! You blocked the author of this post with id of !" + post.getAuthor().getId());
        if (blockService.isYouBeenBlockedBy(currentUser, post.getAuthor())) throw  new BlockedException("Cannot like/unlike! The author of this post with id of " + post.getAuthor().getId() + " already blocked you");

        if (likeService.isUserAlreadyLiked(currentUser, post)) {
            likeService.unlike(currentUser, post);
            return postMapper.toDTO(post);
        }

        likeService.like(currentUser, post);
        return postMapper.toDTO(post);
    }

    public CommentDTO likeComment(int respondentId, int commentId)
            throws ResourceNotFoundException, BlockedException {

        User currentUser = userService.getById(respondentId);
        Comment comment = commentService.getById(commentId);
        if (commentService.isDeleted(comment)) throw new ResourceNotFoundException("Cannot like/unlike! The comment with id of " + comment.getId() + " you are trying to like/unlike might already been deleted or does not exists!");
        if (blockService.isBlockedBy(currentUser, comment.getCommenter())) throw new BlockedException("Cannot like/unlike! You blocked the author of this comment with id of !" + comment.getCommenter().getId());
        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter())) throw new BlockedException("Cannot like/unlike! The author of this comment with id of " + comment.getCommenter().getId() + " already blocked you");

        if (likeService.isUserAlreadyLiked(currentUser, comment)) {
            likeService.unlike(currentUser, comment);
            return commentMapper.toDTO(comment);
        }

        likeService.like(currentUser, comment);
        return commentMapper.toDTO(comment);
    }

    public ReplyDTO likeReply(int respondentId, int replyId)
            throws ResourceNotFoundException, BlockedException {

        User currentUser = userService.getById(respondentId);
        Reply reply = replyService.getById(replyId);
        if (replyService.isDeleted(reply)) throw new ResourceNotFoundException("Cannot like/unlike! The reply with id of " + reply.getId() + " you are trying to like/unlike might already be deleted or does not exists!");
        if (blockService.isBlockedBy(currentUser, reply.getReplier())) throw new BlockedException("Cannot like/unlike! You blocked the author of this reply with id of !" + reply.getReplier().getId());
        if (blockService.isYouBeenBlockedBy(currentUser, reply.getReplier())) throw  new BlockedException("Cannot like/unlike! The author of this reply with id of " + reply.getReplier().getId() + " already blocked you");

        if (likeService.isUserAlreadyLiked(currentUser, reply)) {
            likeService.unlike(currentUser, reply);
            return replyMapper.toDTO(reply);
        }

        likeService.like(currentUser, reply);
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

    public UserDTO getByUUID(String UUID) {
        if (StringValidator.isNotValidBody(UUID)) throw new IllegalArgumentException("UUID cannot be empty, null, or blank");
        User user = userService.getByUUID(UUID);
        return userMapper.toDTO(user);
    }

    public PostDTO pinComment(int currentUserId, int postId, int commentId)
            throws ResourceNotFoundException, NotOwnedException {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        if (postService.isUserNotOwnedPost(currentUser, post)) throw new NotOwnedException("User with id of " + currentUserId + " does not own post with id of " + postId + " for him/her to pin a comment in this post!");
        if (!postService.isHasComment(post, comment)) throw new NotOwnedException("Post with id of " + postId + " doesn't have comment with id of " + commentId);
        if (commentService.isDeleted(comment)) throw new ResourceNotFoundException("Comment with id of " + commentId + " you specify is already deleted or doesn't exist anymore!");

        postService.pinComment(post, comment);
        return postMapper.toDTO(post);
    }

    public CommentDTO pinReply(int currentUserId, int commentId, int replyId)
            throws ResourceNotFoundException, NotOwnedException {

        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.getById(replyId);

        if (commentService.isUserNotOwnedComment(currentUser, comment)) throw new NotOwnedException("User with id of " + currentUserId + " does not owned comment with id of " + commentId + " for him/her to pin a reply in this comment!");
        if (!commentService.isHasReply(comment, reply)) throw new NotOwnedException("Comment with id of " + commentId + " doesnt have reply of " + replyId);
        if (replyService.isDeleted(reply)) throw new ResourceNotFoundException("Reply with id of " + replyId + " you specify is already deleted or does not exists anymore!");

        commentService.pinReply(comment, reply);
        return commentMapper.toDTO(comment);
    }

    public Optional<CommentDTO> getPinnedComment(int postId) throws ResourceNotFoundException {
        Post post = postService.getById(postId);
        Comment pinnedComment = post.getPinnedComment();
        if (postService.isDeleted(post)) throw new ResourceNotFoundException("Post with id of " + postId + " might already been deleted or does not exists anymore!");

        if (pinnedComment == null) return Optional.empty();
        return Optional.of( commentMapper.toDTO(pinnedComment) );
    }

    public Optional<ReplyDTO> getPinnedReply(int commentId) throws ResourceNotFoundException {
        Comment comment = commentService.getById(commentId);
        Reply pinnedReply = comment.getPinnedReply();
        if (commentService.isDeleted(comment)) throw new ResourceNotFoundException("Comment with id of " + commentId + " might already been deleted or does not exists!");

        if (pinnedReply == null) return Optional.empty();
        return Optional.of( replyMapper.toDTO(pinnedReply) );
    }
}
