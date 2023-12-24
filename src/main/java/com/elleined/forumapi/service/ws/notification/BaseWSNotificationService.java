package com.elleined.forumapi.service.ws.notification;

import org.springframework.messaging.simp.SimpMessagingTemplate;

public abstract class BaseWSNotificationService {

    protected final SimpMessagingTemplate simpMessagingTemplate;

    protected BaseWSNotificationService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
}
