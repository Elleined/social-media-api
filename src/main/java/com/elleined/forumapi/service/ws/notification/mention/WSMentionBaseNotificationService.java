package com.elleined.forumapi.service.ws.notification.mention;

import com.elleined.forumapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.forumapi.service.ws.notification.BaseWSNotificationService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

abstract class WSMentionBaseNotificationService extends BaseWSNotificationService {

    protected final MentionNotificationMapper mentionNotificationMapper;
    protected WSMentionBaseNotificationService(SimpMessagingTemplate simpMessagingTemplate, MentionNotificationMapper mentionNotificationMapper) {
        super(simpMessagingTemplate);
        this.mentionNotificationMapper = mentionNotificationMapper;
    }
}
