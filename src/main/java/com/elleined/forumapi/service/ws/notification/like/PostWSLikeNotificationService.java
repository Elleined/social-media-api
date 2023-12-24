package com.elleined.forumapi.service.ws.notification.like;

import com.elleined.forumapi.dto.notification.PostNotification;
import com.elleined.forumapi.mapper.notification.like.LikeNotificationMapper;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.like.PostLike;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostWSLikeNotificationService extends WSLikeBaseNotificationService implements WSLikeNotificationService<PostLike> {


    public PostWSLikeNotificationService(SimpMessagingTemplate simpMessagingTemplate, LikeNotificationMapper likeNotificationMapper) {
        super(simpMessagingTemplate, likeNotificationMapper);
    }

    @Override
    public void broadcast(PostLike postLike) {
        if (postLike.getNotificationStatus() == NotificationStatus.READ) return;
        PostNotification postNotification = likeNotificationMapper.toNotification(postLike);
        simpMessagingTemplate.convertAndSend(LIKE_NOTIFICATION_DESTINATION + postLike.getReceiverId(), postNotification);
        log.debug("Post like notification successfully sent to post author with id of {}", postLike.getReceiverId());
    }
}
