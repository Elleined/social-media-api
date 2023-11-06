package com.elleined.forumapi.service.ws;

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

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class WSNotificationService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationMapper notificationMapper;

    private static final String LIKE_NOTIFICATION_DESTINATION = "/notification/likes/";
    private static final String MENTION_NOTIFICATION_DESTINATION = "/notification/mentions/";


    public void broadcastCommentNotification(Comment comment) throws ResourceNotFoundException {
        if (comment.getNotificationStatus() == NotificationStatus.READ) return; // If the post author replied in his own post it will not generate a notification block
        CommentNotification commentNotificationResponse = notificationMapper.toNotification(comment);
        int authorId = comment.getPost().getAuthor().getId();
        final String destination = "/notification/comments/" + authorId;
        simpMessagingTemplate.convertAndSend(destination, commentNotificationResponse);
        log.debug("Comment notification successfully sent to author with id of {}", authorId);
    }

    public void broadcastReplyNotification(Reply reply) throws ResourceNotFoundException {
        if (reply.getNotificationStatus() == NotificationStatus.READ) return;
        ReplyNotification replyNotificationResponse = notificationMapper.toNotification(reply);
        int commenterId = reply.getComment().getCommenter().getId();
        final String destination = "/notification/replies/" + commenterId;
        simpMessagingTemplate.convertAndSend(destination, replyNotificationResponse);
        log.debug("Reply notification successfully sent to commenter with id of {}", commenterId);
    }


    public void broadcastLike(PostLike postLike) {
        if (postLike.getNotificationStatus() == NotificationStatus.READ) return;
        PostNotification postNotification = notificationMapper.toLikeNotification(postLike);
        simpMessagingTemplate.convertAndSend(LIKE_NOTIFICATION_DESTINATION + postLike.getReceiverId(), postNotification);
        log.debug("Post like notification successfully sent to post author with id of {}", postLike.getReceiverId());
    }


    public void broadcastLike(CommentLike commentLike) {
        if (commentLike.getNotificationStatus() == NotificationStatus.READ) return;
        CommentNotification commentNotification = notificationMapper.toLikeNotification(commentLike);
        simpMessagingTemplate.convertAndSend(LIKE_NOTIFICATION_DESTINATION + commentLike.getReceiverId());
        log.debug("Comment like notification successfully sent to comment author with id of {}", commentLike.getReceiverId());
    }


    public void broadcastLike(ReplyLike replyLike) {
        if (replyLike.getNotificationStatus() == NotificationStatus.READ) return;
        ReplyNotification replyNotification = notificationMapper.toLikeNotification(replyLike);
        simpMessagingTemplate.convertAndSend(LIKE_NOTIFICATION_DESTINATION + replyNotification.getReceiverId(), replyNotification);
        log.debug("Reply like notification successfully sent to reply author with id of {}", replyNotification.getReceiverId());
    }

    public void broadcastPostMentions(Set<PostMention> postMentions) {
        postMentions.forEach(this::broadcastMention);
    }

    private void broadcastMention(PostMention postMention) {
        if (postMention.getNotificationStatus() == NotificationStatus.READ) return;
        PostNotification postNotification = notificationMapper.toMentionNotification(postMention);
        simpMessagingTemplate.convertAndSend(MENTION_NOTIFICATION_DESTINATION + postNotification.getReceiverId(), postNotification);
        log.debug("Post mention notification successfully sent to mentioned user with id of {}", postNotification.getReceiverId());
    }

   public void broadcastCommentMentions(Set<CommentMention> commentMentions) {
        commentMentions.forEach(this::broadcastMention);
    }

    private void broadcastMention(CommentMention commentMention) {
        if (commentMention.getNotificationStatus() == NotificationStatus.READ) return;
        CommentNotification commentNotification = notificationMapper.toMentionNotification(commentMention);
        simpMessagingTemplate.convertAndSend(MENTION_NOTIFICATION_DESTINATION + commentNotification.getReceiverId(), commentNotification);
        log.debug("Comment mention notification successfully sent to mentioned user with id of {}", commentMention.getReceiverId());
    }

    public void broadcastReplyMentions(Set<ReplyMention> replyMentions) {
        replyMentions.forEach(this::broadcastMention);
    }

    private void broadcastMention(ReplyMention replyMention) {
        if (replyMention.getNotificationStatus() == NotificationStatus.READ) return;
        ReplyNotification replyNotification = notificationMapper.toMentionNotification(replyMention);
        simpMessagingTemplate.convertAndSend(MENTION_NOTIFICATION_DESTINATION + replyNotification.getReceiverId(), replyNotification);
        log.debug("Reply mention notification successfully sent to mentioned user with id of {}", replyNotification.getReceiverId());
    }
}
