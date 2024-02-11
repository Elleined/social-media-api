package com.elleined.forumapi.service.notification;

import com.elleined.forumapi.dto.notification.*;
import com.elleined.forumapi.mapper.notification.comment.CommentNotificationMapper;
import com.elleined.forumapi.mapper.notification.friend.FriendRequestNotificationMapper;
import com.elleined.forumapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.forumapi.mapper.notification.react.ReactNotificationMapper;
import com.elleined.forumapi.mapper.notification.reply.ReplyNotificationMapper;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.notification.comment.CommentNotificationService;
import com.elleined.forumapi.service.notification.friend.FriendRequestNotificationService;
import com.elleined.forumapi.service.notification.mention.CommentMentionNotificationService;
import com.elleined.forumapi.service.notification.mention.PostMentionNotificationService;
import com.elleined.forumapi.service.notification.mention.ReplyMentionNotificationService;
import com.elleined.forumapi.service.notification.react.CommentReactNotificationService;
import com.elleined.forumapi.service.notification.react.PostReactNotificationService;
import com.elleined.forumapi.service.notification.react.ReplyReactNotificationService;
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
    // Notification Services

    // Mentions
    private final PostMentionNotificationService postMentionNotificationService;
    private final CommentMentionNotificationService commentMentionNotificationService;
    private final ReplyMentionNotificationService replyMentionNotificationService;

    // Reactions
    private final PostReactNotificationService postReactNotificationService;
    private final CommentReactNotificationService commentReactNotificationService;
    private final ReplyReactNotificationService replyReactNotificationService;

    // Entities
    private final CommentNotificationService commentNotificationService;
    private final ReplyNotificationService replyNotificationService;

    private final FriendRequestNotificationService friendRequestNotificationService;

    // Mappers
    private final CommentNotificationMapper commentNotificationMapper;
    private final ReplyNotificationMapper replyNotificationMapper;

    private final MentionNotificationMapper mentionNotificationMapper;

    private final FriendRequestNotificationMapper friendRequestNotificationMapper;

    private final ReactNotificationMapper reactNotificationMapper;

    @Override
    public Set<Notification> getAllUnreadNotification(User currentUser) {
        // Entities
        Set<CommentNotification> unreadComments = commentNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(commentNotificationMapper::toNotification)
                .collect(Collectors.toSet());
        Set<ReplyNotification> unreadReply = replyNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(replyNotificationMapper::toNotification)
                .collect(Collectors.toSet());

        // Mentions
        Set<PostNotification> unreadPostMentions = postMentionNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(mentionNotificationMapper::toNotification)
                .collect(Collectors.toSet());
        Set<CommentNotification> unreadCommentMentions = commentMentionNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(mentionNotificationMapper::toNotification)
                .collect(Collectors.toSet());
        Set<ReplyNotification> unreadReplyMentions = replyMentionNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(mentionNotificationMapper::toNotification)
                .collect(Collectors.toSet());

        // Reactions
        Set<ReactionNotification> unreadPostReactions = postReactNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(reactNotificationMapper::toNotification)
                .collect(Collectors.toSet());
        Set<ReactionNotification> unreadCommentReactions = commentReactNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(reactNotificationMapper::toNotification)
                .collect(Collectors.toSet());
        Set<ReactionNotification> unreadReplyReactions = replyReactNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(reactNotificationMapper::toNotification)
                .collect(Collectors.toSet());

        // Friend Request
        Set<FriendRequestNotification> unreadFriendRequests = friendRequestNotificationService.getAllUnreadNotification(currentUser).stream()
                .map(friendRequestNotificationMapper::toSendNotification)
                .collect(Collectors.toSet());
        
        return Stream.of(
                        unreadComments,
                        unreadReply,
                        unreadPostMentions,
                        unreadCommentMentions,
                        unreadReplyMentions,
                        unreadPostReactions,
                        unreadCommentReactions,
                        unreadReplyReactions,
                        unreadFriendRequests)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
