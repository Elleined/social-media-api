package com.elleined.forumapi.service.ws.notification;

import com.elleined.forumapi.dto.notification.ReplyNotification;
import com.elleined.forumapi.mapper.notification.ReplyNotificationMapper;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Reply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReplyWSNotificationService extends BaseWSNotificationService implements WSNotificationService<Reply> {

    private final ReplyNotificationMapper replyNotificationMapper;

    public ReplyWSNotificationService(SimpMessagingTemplate simpMessagingTemplate, ReplyNotificationMapper replyNotificationMapper) {
        super(simpMessagingTemplate);
        this.replyNotificationMapper = replyNotificationMapper;
    }

    @Override
    public void broadcast(Reply reply) {
        if (reply.getNotificationStatus() == NotificationStatus.READ) return;
        ReplyNotification replyNotificationResponse = replyNotificationMapper.toNotification(reply);
        int commenterId = reply.getComment().getCommenter().getId();
        final String destination = "/notification/replies/" + commenterId;
        simpMessagingTemplate.convertAndSend(destination, replyNotificationResponse);
        log.debug("Reply notification successfully sent to commenter with id of {}", commenterId);
    }
}
