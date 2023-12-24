package com.elleined.forumapi.service.ws.notification.like;

import com.elleined.forumapi.dto.notification.CommentNotification;
import com.elleined.forumapi.mapper.notification.like.LikeNotificationMapper;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.like.CommentLike;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommentWSLikeNotificationService extends WSLikeBaseNotificationService implements WSLikeNotificationService<CommentLike> {

    public CommentWSLikeNotificationService(SimpMessagingTemplate simpMessagingTemplate, LikeNotificationMapper likeNotificationMapper) {
        super(simpMessagingTemplate, likeNotificationMapper);
    }

    @Override
    public void broadcast(CommentLike commentLike) {
        if (commentLike.getNotificationStatus() == NotificationStatus.READ) return;
        CommentNotification commentNotification = likeNotificationMapper.toNotification(commentLike);
        simpMessagingTemplate.convertAndSend(LIKE_NOTIFICATION_DESTINATION + commentLike.getReceiverId(), commentNotification);
        log.debug("Comment like notification successfully sent to comment author with id of {}", commentLike.getReceiverId());
    }
}
