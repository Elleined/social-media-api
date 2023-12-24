package com.elleined.forumapi.service.ws.notification.mention;

import com.elleined.forumapi.dto.notification.ReplyNotification;
import com.elleined.forumapi.mapper.NotificationMapper;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.mention.ReplyMention;
import com.elleined.forumapi.service.ws.notification.BaseWSNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class ReplyMentionWSNotificationService extends BaseWSNotificationService implements MentionWSNotificationService<ReplyMention> {

    public ReplyMentionWSNotificationService(SimpMessagingTemplate simpMessagingTemplate, NotificationMapper notificationMapper) {
        super(simpMessagingTemplate, notificationMapper);
    }

    @Override
    public void broadcast(ReplyMention replyMention) {
        if (replyMention.getNotificationStatus() == NotificationStatus.READ) return;
        ReplyNotification replyNotification = notificationMapper.toMentionNotification(replyMention);
        simpMessagingTemplate.convertAndSend(MENTION_NOTIFICATION_DESTINATION + replyNotification.getReceiverId(), replyNotification);
        log.debug("Reply mention notification successfully sent to mentioned user with id of {}", replyNotification.getReceiverId());
    }

    @Override
    public void broadcastMentions(Set<ReplyMention> mentions) {
        mentions.forEach(this::broadcast);
    }
}
