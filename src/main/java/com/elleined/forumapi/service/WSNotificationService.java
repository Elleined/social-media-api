package com.elleined.forumapi.service;

import com.elleined.forumapi.dto.NotificationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WSNotificationService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    void broadcastNotification(NotificationResponse notificationResponse) {
        int receiverId = notificationResponse.getReceiverId();
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(receiverId), "/notification/comments", notificationResponse);
        log.debug("Comment notification successfully sent to author with id of {}", receiverId);
    }
}
