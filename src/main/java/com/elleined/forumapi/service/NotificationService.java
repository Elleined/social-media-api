package com.elleined.forumapi.service;

import com.elleined.forumapi.dto.notification.CommentNotification;
import com.elleined.forumapi.dto.notification.Notification;
import com.elleined.forumapi.dto.notification.PostNotification;
import com.elleined.forumapi.dto.notification.ReplyNotification;
import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.mapper.NotificationMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

    public Set<Notification> getAllNotification(User currentUser) {
        Set<CommentNotification> unreadComments = commentService.getUnreadCommentsOfAllPost(currentUser).stream()
                .map(notificationMapper::toNotification)
                .collect(Collectors.toSet());

        Set<ReplyNotification> unreadReply = replyService.getUnreadRepliesOfAllComments(currentUser).stream()
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

    public CommentNotification getCommentNotification(int commentId) throws ResourceNotFoundException {
        Comment comment = commentService.getById(commentId);
        return notificationMapper.toNotification(comment);
    }

    public ReplyNotification getReplyNotification(int replyId) throws ResourceNotFoundException {
        Reply reply = replyService.getById(replyId);
        return notificationMapper.toNotification(reply);
    }

    public Set<PostNotification> getPostLikeNotification(int currentUserId, int postId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        return likeService.getUnreadLikes(currentUser, post).stream()
                .map(notificationMapper::toLikeNotification)
                .collect(Collectors.toSet());
    }

    public Set<CommentNotification> getCommentLikeNotification(int currentUserId, int commentId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);

        return likeService.getUnreadLikes(currentUser, comment).stream()
                .map(notificationMapper::toLikeNotification)
                .collect(Collectors.toSet());
    }

    public Set<ReplyNotification> getReplyLikeNotification(int currentUserId, int replyId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        Reply reply = replyService.getById(replyId);
        return likeService.getUnreadLikes(currentUser, reply).stream()
                .map(notificationMapper::toLikeNotification)
                .collect(Collectors.toSet());
    }

    public Set<PostNotification> getPostMentionsNotification(int currentUserId, int postId) throws ResourceNotFoundException {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        return mentionService.getUnreadMentions(currentUser, post).stream()
                .map(notificationMapper::toMentionNotification)
                .collect(Collectors.toSet());
    }

    public Set<CommentNotification> getCommentMentionsNotification(int currentUserId, int commentId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);
        return mentionService.getUnreadMentions(currentUser, comment).stream()
                .map(notificationMapper::toMentionNotification)
                .collect(Collectors.toSet());
    }

    public Set<ReplyNotification> getReplyMentionsNotification(int currentUserId, int replyId) throws ResourceNotFoundException {
        User currentUser = userService.getById(currentUserId);
        Reply reply = replyService.getById(replyId);
        return mentionService.getUnreadMentions(currentUser, reply).stream()
                .map(notificationMapper::toMentionNotification)
                .collect(Collectors.toSet());
    }
}
