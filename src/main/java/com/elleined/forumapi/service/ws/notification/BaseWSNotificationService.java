package com.elleined.forumapi.service.ws.notification;

import com.elleined.forumapi.mapper.NotificationMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public abstract class BaseWSNotificationService {

    protected final SimpMessagingTemplate simpMessagingTemplate;

    protected final NotificationMapper notificationMapper;

    protected BaseWSNotificationService(SimpMessagingTemplate simpMessagingTemplate, NotificationMapper notificationMapper) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationMapper = notificationMapper;
    }
}
