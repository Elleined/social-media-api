package com.elleined.socialmediaapi.service.ws.notification;

import com.elleined.socialmediaapi.dto.notification.main.CommentNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.main.ReplyNotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationWSServiceImpl implements NotificationWSService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void notifyOnComment(CommentNotificationDTO commentNotificationDTO) {
        if (commentNotificationDTO.isRead())
            return;

        commentNotificationDTO.setMessage(HtmlUtils.htmlEscape(commentNotificationDTO.getMessage()));
        int receiverId = commentNotificationDTO.getReceiverId();

        final String destination = STR."/sma-notification/users/\{receiverId}";
        simpMessagingTemplate.convertAndSend(destination, commentNotificationDTO);
        log.debug("Broadcasting comment notification success");
    }

    @Override
    public void notifyOnReply(ReplyNotificationDTO replyNotificationDTO) {
        if (replyNotificationDTO.isRead())
            return;

        replyNotificationDTO.setMessage(HtmlUtils.htmlEscape(replyNotificationDTO.getMessage()));
        int receiverId = replyNotificationDTO.getReceiverId();

        final String destination = STR."/sma-notification/users/\{receiverId}";
        simpMessagingTemplate.convertAndSend(destination, replyNotificationDTO);
        log.debug("Broadcasting reply notification success");
    }
}
