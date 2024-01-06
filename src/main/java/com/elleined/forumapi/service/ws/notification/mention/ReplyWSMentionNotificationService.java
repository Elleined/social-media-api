package com.elleined.forumapi.service.ws.notification.mention;

import com.elleined.forumapi.dto.notification.ReplyNotification;
import com.elleined.forumapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.forumapi.model.mention.ReplyMention;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReplyWSMentionNotificationService extends WSMentionBaseNotificationService implements WSMentionNotificationService<ReplyMention> {

    public ReplyWSMentionNotificationService(SimpMessagingTemplate simpMessagingTemplate, MentionNotificationMapper mentionNotificationMapper) {
        super(simpMessagingTemplate, mentionNotificationMapper);
    }

    @Override
    public void broadcast(ReplyMention replyMention) {
        if (replyMention.isRead()) return;
        ReplyNotification replyNotification = mentionNotificationMapper.toNotification(replyMention);
        simpMessagingTemplate.convertAndSend(MENTION_NOTIFICATION_DESTINATION + replyNotification.getReceiverId(), replyNotification);
        log.debug("Reply mention notification successfully sent to mentioned user with id of {}", replyNotification.getReceiverId());
    }
}
