package com.forum.application.service;

import com.forum.application.dto.*;
import com.forum.application.exception.*;
import com.forum.application.mapper.CommentMapper;
import com.forum.application.mapper.PostMapper;
import com.forum.application.mapper.ReplyMapper;
import com.forum.application.mapper.UserMapper;
import com.forum.application.model.Comment;
import com.forum.application.model.Post;
import com.forum.application.model.Reply;
import com.forum.application.model.User;
import com.forum.application.model.like.Like;
import com.forum.application.validator.Validator;
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
    private final NotificationService notificationService;

    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final ReplyMapper replyMapper;
    private final UserMapper userMapper;

    public PostDTO savePost(int currentUserId, String body, String attachedPicture, Set<Integer> mentionedUserIds) throws EmptyBodyException,
            BlockedException,
            ResourceNotFoundException {

        User currentUser = userService.getById(currentUserId);
        if (Validator.isValidBody(body)) throw new EmptyBodyException("Body cannot be empty! Please provide text for your post to be posted!");

        Post post = postService.save(currentUser, body, attachedPicture);
        if (mentionedUserIds != null) mentionService.addAllMention(currentUser, mentionedUserIds, post);
        return postMapper.toDTO(post);
    }

    public CommentDTO saveComment(int currentUserId, int postId, String body, String attachedPicture, Set<Integer> mentionedUserIds) throws ResourceNotFoundException,
            ClosedCommentSectionException,
            BlockedException,
            EmptyBodyException {

        User currentUser = userService.getById(currentUserId);
        if (Validator.isValidBody(body)) throw new EmptyBodyException("Comment body cannot be empty! Please provide text for your comment");
        if (postService.isCommentSectionClosed(postId)) throw new ClosedCommentSectionException("Cannot comment because author already closed the comment section for this post!");
        if (postService.isDeleted(postId)) throw new ResourceNotFoundException("The post you trying to comment is either be deleted or does not exists anymore!");
        if (blockService.isBlockedBy(currentUser, postService.getById(postId).getAuthor()))  throw new BlockedException("Cannot comment because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, postService.getById(postId).getAuthor())) throw new BlockedException("Cannot comment because this user block you already!");

        Comment comment = commentService.save(currentUser, postId, body, attachedPicture);
        if (mentionedUserIds != null) mentionService.addAllMention(currentUser, mentionedUserIds, comment);
        return commentMapper.toDTO(comment);
    }

    public ReplyDTO saveReply(int currentUserId, int commentId, String body, String attachedPicture, Set<Integer> mentionedUserIds) throws EmptyBodyException,
            ClosedCommentSectionException,
            ResourceNotFoundException,
            BlockedException {
        User currentUser = userService.getById(currentUserId);

        if (Validator.isValidBody(body)) throw new EmptyBodyException("Reply body cannot be empty!");
        if (commentService.isCommentSectionClosed(commentId)) throw new ClosedCommentSectionException("Cannot reply to this comment because author already closed the comment section for this post!");
        if (commentService.isDeleted(commentId)) throw new ResourceNotFoundException("The comment you trying to reply is either be deleted or does not exists anymore!");
        if (blockService.isBlockedBy(currentUser, commentService.getById(commentId).getCommenter())) throw new BlockedException("Cannot reply because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, commentService.getById(commentId).getCommenter())) throw new BlockedException("Cannot reply because this user block you already!");

        Reply reply = replyService.save(currentUser, commentId, body, attachedPicture);
        if (mentionedUserIds != null) mentionService.addAllMention(currentUser, mentionedUserIds, reply);
        return replyMapper.toDTO(reply);
    }

    public PostDTO getPostById(int postId) {
        Post post = postService.getById(postId);
        return postMapper.toDTO(post);
    }
    public CommentDTO getCommentById(int commentId) {
        Comment comment = commentService.getById(commentId);
        return commentMapper.toDTO(comment);
    }

    public ReplyDTO getReplyById(int replyId) {
        Reply reply = replyService.getById(replyId);
        return replyMapper.toDTO(reply);
    }

    public void deletePost(int postId) {
        postService.delete(postId);
    }

    public CommentDTO deleteComment(int commentId) {
        Comment comment = commentService.delete(commentId);
        return commentMapper.toDTO(comment);
    }

    public ReplyDTO deleteReply(int replyId) {
        Reply reply = replyService.delete(replyId);
        return replyMapper.toDTO(reply);
    }

    public List<PostDTO> getAllByAuthorId(int authorId) {
        return postService.getAllByAuthorId(authorId)
                .stream()
                .map(postMapper::toDTO)
                .toList();
    }

    public List<PostDTO> getAllPost(int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        likeService.readLikes(currentUser);
        mentionService.readMentions(currentUser);
        return postService.getAll(currentUser)
                .stream()
                .map(postMapper::toDTO)
                .toList();
    }

    public List<CommentDTO> getAllCommentsOf(int currentUserId, int postId) {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        commentService.readAllComments(currentUser, post);
         likeService.readLikes(currentUser, post);
        mentionService.readMentions(currentUser, post);
        return commentService.getAllCommentsOf(currentUser, post).stream()
                .map(commentMapper::toDTO)
                .toList();
    }

    public List<ReplyDTO> getAllRepliesOf(int currentUserId, int commentId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);

        replyService.readAllReplies(currentUser, comment);
        likeService.readLikes(currentUser, comment);
        mentionService.readMentions(currentUser, comment);
        return replyService.getAllRepliesOf(currentUser, comment).stream()
                .map(replyMapper::toDTO)
                .toList();
    }

    public long getTotalNotificationCount(User currentUser) throws ResourceNotFoundException {
        return notificationService.getTotalNotificationCount(currentUser);
    }

    public Set<NotificationResponse> getAllNotification(User currentUser) throws ResourceNotFoundException {
        return notificationService.getAllNotification(currentUser);
    }

    public CommentDTO updateUpvote(int currentUserId, int commentId) throws NoLoggedInUserException,
            ResourceNotFoundException,
            UpvoteException {

        User currentUser = userService.getById(currentUserId);
        if (commentService.isDeleted(commentId)) throw new ResourceNotFoundException("The comment you trying to upvote might be deleted by the author or does not exists anymore!");
        if (commentService.isUserAlreadyUpvoteComment(currentUser, commentId)) throw new UpvoteException("You can only up vote and down vote a comment once!");

        int updatedCommentId = commentService.updateUpvote(currentUser, commentId);
        Comment comment = commentService.getById(updatedCommentId);
        return commentMapper.toDTO(comment);
    }

    public PostDTO updateCommentSectionStatus(int postId, Post.CommentSectionStatus status) {
        Post post = postService.updateCommentSectionStatus(postId, status);
        return postMapper.toDTO(post);
    }

    public PostDTO updatePostBody(int postId, String newBody) {
        Post post = postService.updatePostBody(postId, newBody);
        return postMapper.toDTO(post);
    }

    public CommentDTO updateCommentBody(int commentId, String newBody) {
        Comment comment = commentService.updateCommentBody(commentId, newBody);
        return commentMapper.toDTO(comment);
    }

    public ReplyDTO updateReplyBody(int replyId, String newReplyBody) {
        Reply reply = replyService.updateReplyBody(replyId, newReplyBody);
        return replyMapper.toDTO(reply);
    }

    public String getCommentSectionStatus(int postId) {
        return postService.getCommentSectionStatus(postId);
    }

    public List<UserDTO> getAllUser(int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return userService.getAllUser(currentUser)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public List<UserDTO> getSuggestedMentions(int currentUserId, String name) {
        User currentUser = userService.getById(currentUserId);
        return userService.getSuggestedMentions(currentUser, name)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public void blockUser(int currentUserId, int userToBeBlockedId) throws ResourceNotFoundException, BlockedException {
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

    public Optional<Like> likePost(int respondentId, int postId) throws ResourceNotFoundException, BlockedException {
        User currentUser = userService.getById(respondentId);
        Post post = postService.getById(postId);

        return likeService.like(post, currentUser);
    }

    public Optional<Like> likeComment(int respondentId, int commentId) throws ResourceNotFoundException, BlockedException {
        User currentUser = userService.getById(respondentId);
        Comment comment = commentService.getById(commentId);

        return likeService.like(comment, currentUser);
    }

    public Optional<Like> likeReply(int respondentId, int replyId) throws ResourceNotFoundException, BlockedException {
        User currentUser = userService.getById(respondentId);
        Reply reply = replyService.getById(replyId);

        return likeService.like(reply, currentUser);
    }
}
