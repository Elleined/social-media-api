package com.elleined.forumapi.service.ws.notification.mention;

import com.elleined.forumapi.dto.notification.CommentNotification;
import com.elleined.forumapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.mention.CommentMention;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentWSMentionNotificationService extends WSMentionBaseNotificationService implements WSMentionNotificationService<CommentMention> {

    protected CommentWSMentionNotificationService(SimpMessagingTemplate simpMessagingTemplate, MentionNotificationMapper mentionNotificationMapper) {
        super(simpMessagingTemplate, mentionNotificationMapper);
    }

    @Override
    public void broadcast(CommentMention commentMention) {
        if (commentMention.getNotificationStatus() == NotificationStatus.READ) return;
        CommentNotification commentNotification = mentionNotificationMapper.toNotification(commentMention);
        simpMessagingTemplate.convertAndSend(MENTION_NOTIFICATION_DESTINATION + commentNotification.getReceiverId(), commentNotification);
        log.debug("Comment mention notification successfully sent to mentioned user with id of {}", commentMention.getReceiverId());
    }
}
