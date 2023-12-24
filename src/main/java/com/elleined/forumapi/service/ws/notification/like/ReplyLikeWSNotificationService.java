package com.elleined.forumapi.service.ws.notification.like;

import com.elleined.forumapi.dto.notification.ReplyNotification;
import com.elleined.forumapi.mapper.NotificationMapper;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.like.ReplyLike;
import com.elleined.forumapi.service.ws.notification.BaseWSNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReplyLikeWSNotificationService extends BaseWSNotificationService implements LikeWSNotificationService<ReplyLike> {

    public ReplyLikeWSNotificationService(SimpMessagingTemplate simpMessagingTemplate, NotificationMapper notificationMapper) {
        super(simpMessagingTemplate, notificationMapper);
    }

    @Override
    public void broadcast(ReplyLike replyLike) {
        if (replyLike.getNotificationStatus() == NotificationStatus.READ) return;
        ReplyNotification replyNotification = notificationMapper.toLikeNotification(replyLike);
        simpMessagingTemplate.convertAndSend(LIKE_NOTIFICATION_DESTINATION + replyNotification.getReceiverId(), replyNotification);
        log.debug("Reply like notification successfully sent to reply author with id of {}", replyNotification.getReceiverId());
    }
}
