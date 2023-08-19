package com.elleined.forumapi.service;

import com.elleined.forumapi.dto.notification.CommentNotification;
import com.elleined.forumapi.dto.notification.Notification;
import com.elleined.forumapi.dto.notification.PostNotification;
import com.elleined.forumapi.dto.notification.ReplyNotification;
import com.elleined.forumapi.mapper.NotificationMapper;
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
}
