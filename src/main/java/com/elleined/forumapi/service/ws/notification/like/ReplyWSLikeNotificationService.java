package com.elleined.forumapi.service.ws.notification.like;

import com.elleined.forumapi.dto.notification.ReplyNotification;
import com.elleined.forumapi.mapper.notification.like.LikeNotificationMapper;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.like.ReplyLike;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReplyWSLikeNotificationService extends WSLikeBaseNotificationService implements WSLikeNotificationService<ReplyLike> {

    public ReplyWSLikeNotificationService(SimpMessagingTemplate simpMessagingTemplate, LikeNotificationMapper likeNotificationMapper) {
        super(simpMessagingTemplate, likeNotificationMapper);
    }

    @Override
    public void broadcast(ReplyLike replyLike) {
        if (replyLike.getNotificationStatus() == NotificationStatus.READ) return;
        ReplyNotification replyNotification = likeNotificationMapper.toNotification(replyLike);
        simpMessagingTemplate.convertAndSend(LIKE_NOTIFICATION_DESTINATION + replyNotification.getReceiverId(), replyNotification);
        log.debug("Reply like notification successfully sent to reply author with id of {}", replyNotification.getReceiverId());
    }
}
