package com.elleined.forumapi.service.ws.notification.like;

import com.elleined.forumapi.mapper.notification.like.LikeNotificationMapper;
import com.elleined.forumapi.service.ws.notification.BaseWSNotificationService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

abstract class WSLikeBaseNotificationService extends BaseWSNotificationService {
    protected final LikeNotificationMapper likeNotificationMapper;
    protected WSLikeBaseNotificationService(SimpMessagingTemplate simpMessagingTemplate, LikeNotificationMapper likeNotificationMapper) {
        super(simpMessagingTemplate);
        this.likeNotificationMapper = likeNotificationMapper;
    }
}
