package com.elleined.forumapi.service.ws.notification.mention;

import com.elleined.forumapi.dto.notification.CommentNotification;
import com.elleined.forumapi.mapper.NotificationMapper;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.mention.CommentMention;
import com.elleined.forumapi.service.ws.notification.BaseWSNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class CommentMentionWSNotificationService extends BaseWSNotificationService implements MentionWSNotificationService<CommentMention> {

    public CommentMentionWSNotificationService(SimpMessagingTemplate simpMessagingTemplate, NotificationMapper notificationMapper) {
        super(simpMessagingTemplate, notificationMapper);
    }

    @Override
    public void broadcast(CommentMention commentMention) {
        if (commentMention.getNotificationStatus() == NotificationStatus.READ) return;
        CommentNotification commentNotification = notificationMapper.toMentionNotification(commentMention);
        simpMessagingTemplate.convertAndSend(MENTION_NOTIFICATION_DESTINATION + commentNotification.getReceiverId(), commentNotification);
        log.debug("Comment mention notification successfully sent to mentioned user with id of {}", commentMention.getReceiverId());
    }

    @Override
    public void broadcastMentions(Set<CommentMention> mentions) {
        mentions.forEach(this::broadcast);
    }
}
