package com.forum.application.service;

import com.forum.application.dto.CommentDTO;
import com.forum.application.dto.ReplyDTO;
import com.forum.application.dto.notification.CommentNotification;
import com.forum.application.dto.notification.Notification;
import com.forum.application.dto.notification.PostNotification;
import com.forum.application.dto.notification.ReplyNotification;
import com.forum.application.exception.NotOwnedException;
import com.forum.application.exception.ResourceNotFoundException;
import com.forum.application.mapper.CommentMapper;
import com.forum.application.mapper.NotificationMapper;
import com.forum.application.mapper.ReplyMapper;
import com.forum.application.model.Comment;
import com.forum.application.model.Post;
import com.forum.application.model.Reply;
import com.forum.application.model.User;
import com.forum.application.model.like.CommentLike;
import com.forum.application.model.like.PostLike;
import com.forum.application.model.like.ReplyLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final MentionService mentionService;
    private final LikeService likeService;
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final ReplyService replyService;

    private final NotificationMapper notificationMapper;

    private final CommentMapper commentMapper;
    private final ReplyMapper replyMapper;

    public Set<Notification> getAllNotification(User currentUser) {
        Set<CommentNotification> unreadComments = commentService.getUnreadCommentsOfAllPost(currentUser).stream()
                .map(commentMapper::toDTO)
                .map(notificationMapper::toNotification)
                .collect(Collectors.toSet());

        Set<ReplyNotification> unreadReply = replyService.getUnreadRepliesOfAllComments(currentUser).stream()
                .map(replyMapper::toDTO)
                .map(notificationMapper::toNotification)
                .collect(Collectors.toSet());

        Set<PostNotification> unreadPostLikes = likeService.getUnreadPostLikes(currentUser).stream()
                .map(notificationMapper::toLikeNotification)
                .collect(Collectors.toSet());

        Set<CommentNotification> unreadCommentLikes = likeService.getUnreadCommentLikes(currentUser).stream()
                .map(notificationMapper::toLikeNotification)
                .collect(Collectors.toSet());

        Set<ReplyNotification> unreadReplyLikes = likeService.getUnreadReplyLikes(currentUser).stream()
                .map(notificationMapper::toLikeNotification)
                .collect(Collectors.toSet());

        Set<PostNotification> unreadPostMentions = mentionService.getUnreadPostMentions(currentUser).stream()
                .map(notificationMapper::toMentionNotification)
                .collect(Collectors.toSet());

        Set<CommentNotification> unreadCommentMentions = mentionService.getUnreadCommentMentions(currentUser).stream()
                .map(notificationMapper::toMentionNotification)
                .collect(Collectors.toSet());

        Set<ReplyNotification> unreadReplyMentions = mentionService.getUnreadReplyMentions(currentUser).stream()
                .map(notificationMapper::toMentionNotification)
                .collect(Collectors.toSet());

        // mention notification here
        return Stream.of(unreadComments,
                        unreadReply,
                        unreadPostLikes,
                        unreadCommentLikes,
                        unreadReplyLikes,
                        unreadPostMentions,
                        unreadCommentMentions,
                        unreadReplyMentions
                ).flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public long getTotalNotificationCount(User currentUser) {
        return commentService.getUnreadCommentsOfAllPost(currentUser).size() +
                replyService.getUnreadRepliesOfAllComments(currentUser).size() +
                likeService.getUnreadPostLikes(currentUser).size() +
                likeService.getUnreadCommentLikes(currentUser).size() +
                likeService.getUnreadReplyLikes(currentUser).size() +
                mentionService.getUnreadPostMentions(currentUser).size() +
                mentionService.getUnreadCommentMentions(currentUser).size() +
                mentionService.getUnreadReplyMentions(currentUser).size();
    }

    public CommentNotification getNotification(CommentDTO commentDTO) {
        return notificationMapper.toNotification(commentDTO);
    }

    public ReplyNotification getNotification(ReplyDTO replyDTO) {
        return notificationMapper.toNotification(replyDTO);
    }

    public Optional<PostNotification> getPostLikeNotification(int currentUserId, int postId) {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        Optional<PostLike> postLike = likeService.getUnreadLike(currentUser, post);
        if (postLike.isEmpty()) return Optional.empty();

        PostNotification postNotification = notificationMapper.toLikeNotification(postLike.orElseThrow());
        return Optional.of(postNotification);
    }

    public Optional<CommentNotification> getCommentLikeNotification(int currentUserId, int commentId) {
        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);

        Optional<CommentLike> commentLike = likeService.getUnreadLike(currentUser, comment);
        if (commentLike.isEmpty()) return Optional.empty();

        CommentNotification commentNotification = notificationMapper.toLikeNotification(commentLike.orElseThrow());
        return Optional.of(commentNotification);
    }

    public Optional<ReplyNotification> getReplyLikeNotification(int currentUserId, int replyId) {
        User currentUser = userService.getById(currentUserId);
        Reply reply = replyService.getById(replyId);

        Optional<ReplyLike> replyLike = likeService.getUnreadLike(currentUser, reply);
        if (replyLike.isEmpty()) return Optional.empty();

        ReplyNotification replyNotification = notificationMapper.toLikeNotification(replyLike.orElseThrow());
        return Optional.of(replyNotification);
    }

    public Set<PostNotification> getPostMentionsNotification(int currentUserId, int postId)
            throws ResourceNotFoundException, NotOwnedException {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        return mentionService.getUnreadMentions(currentUser, post).stream()
                .map(notificationMapper::toMentionNotification)
                .collect(Collectors.toSet());
    }

    public Set<CommentNotification> getCommentMentionsNotification(int currentUserId, int commentId) {
        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);
        return mentionService.getUnreadMentions(currentUser, comment).stream()
                .map(notificationMapper::toMentionNotification)
                .collect(Collectors.toSet());
    }

    public Set<ReplyNotification> getReplyMentionsNotification(int currentUserId, int replyId) {
        User currentUser = userService.getById(currentUserId);
        Reply reply = replyService.getById(replyId);
        return mentionService.getUnreadMentions(currentUser, reply).stream()
                .map(notificationMapper::toMentionNotification)
                .collect(Collectors.toSet());
    }
}
