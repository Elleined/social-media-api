package com.elleined.forumapi.service.notification;

import com.elleined.forumapi.dto.notification.CommentNotification;
import com.elleined.forumapi.dto.notification.Notification;
import com.elleined.forumapi.dto.notification.PostNotification;
import com.elleined.forumapi.dto.notification.ReplyNotification;
import com.elleined.forumapi.mapper.NotificationMapper;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.CommentLike;
import com.elleined.forumapi.model.like.PostLike;
import com.elleined.forumapi.model.like.ReplyLike;
import com.elleined.forumapi.model.mention.CommentMention;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.model.mention.ReplyMention;
import com.elleined.forumapi.service.notification.comment.CommentNotificationService;
import com.elleined.forumapi.service.notification.like.LikeNotificationService;
import com.elleined.forumapi.service.notification.mention.MentionNotificationService;
import com.elleined.forumapi.service.notification.reply.ReplyNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService<Notification> {
    private final LikeNotificationService<PostLike> postLikeNotificationService;
    private final LikeNotificationService<CommentLike> commentLikeNotificationService;
    private final LikeNotificationService<ReplyLike> replyLikeNotificationService;

    private final MentionNotificationService<PostMention> postMentionNotificationService;
    private final MentionNotificationService<CommentMention> commentMentionNotificationService;
    private final MentionNotificationService<ReplyMention> replyMentionNotificationService;

    private final CommentNotificationService commentNotificationService;
    private final ReplyNotificationService replyNotificationService;

    private final NotificationMapper notificationMapper;

    @Override
    public Set<Notification> getAllUnreadNotification(User currentUser) {
        Set<CommentNotification> unreadComments = commentNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(notificationMapper::toNotification)
                .collect(Collectors.toSet());
        Set<ReplyNotification> unreadReply = replyNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(notificationMapper::toNotification)
                .collect(Collectors.toSet());

        Set<PostNotification> unreadPostLikes = postLikeNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(notificationMapper::toLikeNotification)
                .collect(Collectors.toSet());

        Set<CommentNotification> unreadCommentLikes = commentLikeNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(notificationMapper::toLikeNotification)
                .collect(Collectors.toSet());

        Set<ReplyNotification> unreadReplyLikes = replyLikeNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(notificationMapper::toLikeNotification)
                .collect(Collectors.toSet());

        Set<PostNotification> unreadPostMentions = postMentionNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(notificationMapper::toMentionNotification)
                .collect(Collectors.toSet());

        Set<CommentNotification> unreadCommentMentions = commentMentionNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(notificationMapper::toMentionNotification)
                .collect(Collectors.toSet());

        Set<ReplyNotification> unreadReplyMentions = replyMentionNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(notificationMapper::toMentionNotification)
                .collect(Collectors.toSet());
        
        return Stream.of(
                        unreadComments,
                        unreadReply,
                        unreadPostLikes,
                        unreadCommentLikes,
                        unreadReplyLikes,
                        unreadPostMentions,
                        unreadCommentMentions,
                        unreadReplyMentions)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public int getNotificationCount(User currentUser) {
        return getAllUnreadNotification(currentUser).size();
    }
}
