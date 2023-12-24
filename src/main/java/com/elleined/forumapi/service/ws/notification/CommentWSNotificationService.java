package com.elleined.forumapi.service.ws.notification;

import com.elleined.forumapi.dto.notification.CommentNotification;
import com.elleined.forumapi.mapper.NotificationMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentWSNotificationService extends BaseWSNotificationService implements WSNotificationService<Comment> {

    public CommentWSNotificationService(SimpMessagingTemplate simpMessagingTemplate, NotificationMapper notificationMapper) {
        super(simpMessagingTemplate, notificationMapper);
    }

    @Override
    public void broadcast(Comment comment) {
        if (comment.getNotificationStatus() == NotificationStatus.READ) return; // If the post author replied in his own post it will not generate a notification block
        CommentNotification commentNotificationResponse = notificationMapper.toNotification(comment);
        int authorId = comment.getPost().getAuthor().getId();
        final String destination = "/notification/comments/" + authorId;
        simpMessagingTemplate.convertAndSend(destination, commentNotificationResponse);
        log.debug("Comment notification successfully sent to author with id of {}", authorId);
    }
}
