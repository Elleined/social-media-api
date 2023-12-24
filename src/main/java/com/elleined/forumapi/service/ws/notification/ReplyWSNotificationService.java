package com.elleined.forumapi.service.ws.notification;

import com.elleined.forumapi.dto.notification.ReplyNotification;
import com.elleined.forumapi.mapper.NotificationMapper;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Reply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReplyWSNotificationService extends BaseWSNotificationService implements WSNotificationService<Reply> {
    public ReplyWSNotificationService(SimpMessagingTemplate simpMessagingTemplate, NotificationMapper notificationMapper) {
        super(simpMessagingTemplate, notificationMapper);
    }

    @Override
    public void broadcast(Reply reply) {
        if (reply.getNotificationStatus() == NotificationStatus.READ) return;
        ReplyNotification replyNotificationResponse = notificationMapper.toNotification(reply);
        int commenterId = reply.getComment().getCommenter().getId();
        final String destination = "/notification/replies/" + commenterId;
        simpMessagingTemplate.convertAndSend(destination, replyNotificationResponse);
        log.debug("Reply notification successfully sent to commenter with id of {}", commenterId);
    }
}
