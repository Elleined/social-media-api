package com.elleined.forumapi.service;

import com.elleined.forumapi.dto.notification.CommentNotification;
import com.elleined.forumapi.dto.notification.PostNotification;
import com.elleined.forumapi.dto.notification.ReplyNotification;
import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.mapper.NotificationMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.like.CommentLike;
import com.elleined.forumapi.model.like.PostLike;
import com.elleined.forumapi.model.like.ReplyLike;
import com.elleined.forumapi.model.mention.CommentMention;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.model.mention.ReplyMention;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WSNotificationService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationMapper notificationMapper;

    private static final String LIKE_NOTIFICATION_DESTINATION = "/notification/likes";
    private static final String MENTION_NOTIFICATION_DESTINATION = "/notification/mentions";


    void broadcastCommentNotification(Comment comment) throws ResourceNotFoundException {
        if (comment.getNotificationStatus() == NotificationStatus.READ) return; // If the post author replied in his own post it will not generate a notification block

        var commentNotificationResponse = notificationMapper.toNotification(comment);
        int authorId = comment.getPost().getAuthor().getId();
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(authorId), "/notification/comments", commentNotificationResponse);
        log.debug("Comment notification successfully sent to author with id of {}", authorId);
    }

    void broadcastReplyNotification(Reply reply) throws ResourceNotFoundException {
        if (reply.getNotificationStatus() == NotificationStatus.READ) return;

        var replyNotificationResponse = notificationMapper.toNotification(reply);
        int commenterId = reply.getComment().getCommenter().getId();
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(commenterId), "/notification/replies", replyNotificationResponse);

        log.debug("Reply notification successfully sent to commenter with id of {}", commenterId);
    }


    public void broadcastLike(PostLike postLike) {
        if (postLike.getNotificationStatus() == NotificationStatus.READ) return;

        PostNotification postNotification = notificationMapper.toLikeNotification(postLike);
        int receiverId = postLike.getReceiverId();
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(receiverId), LIKE_NOTIFICATION_DESTINATION, postNotification);
    }


    public void broadcastLike(CommentLike commentLike) {
        if (commentLike.getNotificationStatus() == NotificationStatus.READ) return;

        CommentNotification commentNotification = notificationMapper.toLikeNotification(commentLike);
        int receiverId = commentLike.getReceiverId();
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(receiverId), LIKE_NOTIFICATION_DESTINATION, commentNotification);
    }


    public void broadcastLike(ReplyLike replyLike) {
        if (replyLike.getNotificationStatus() == NotificationStatus.READ) return;

        ReplyNotification replyNotification = notificationMapper.toLikeNotification(replyLike);
        int receiverId = replyLike.getReceiverId();
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(receiverId), LIKE_NOTIFICATION_DESTINATION, replyNotification);
    }

    void broadcastPostMentions(List<PostMention> postMentions) {
        postMentions.forEach(this::broadcastMention);
    }

    private void broadcastMention(PostMention postMention) {
        if (postMention.getNotificationStatus() == NotificationStatus.READ) return;

        PostNotification postNotification = notificationMapper.toMentionNotification(postMention);
        int receiverId = postMention.getReceiverId();
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(receiverId), MENTION_NOTIFICATION_DESTINATION, postNotification);

        log.debug("");
    }


    void broadcastCommentMentions(List<CommentMention> commentMentions) {
        commentMentions.forEach(this::broadcastMention);
    }

    private void broadcastMention(CommentMention commentMention) {
        if (commentMention.getNotificationStatus() == NotificationStatus.READ) return;

        CommentNotification commentNotification = notificationMapper.toMentionNotification(commentMention);
        int receiverId = commentMention.getReceiverId();
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(receiverId), MENTION_NOTIFICATION_DESTINATION, commentNotification);
    }

    void broadcastReplyMentions(List<ReplyMention> replyMentions) {
        replyMentions.forEach(this::broadcastMention);
    }

    private void broadcastMention(ReplyMention replyMention) {
        if (replyMention.getNotificationStatus() == NotificationStatus.READ) return;

        ReplyNotification replyNotification = notificationMapper.toMentionNotification(replyMention);
        int receiverId = replyMention.getReceiverId();
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(receiverId), MENTION_NOTIFICATION_DESTINATION, replyNotification);
    }
}
