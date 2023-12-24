package com.elleined.forumapi.service.ws.notification.mention;

import com.elleined.forumapi.dto.notification.PostNotification;
import com.elleined.forumapi.mapper.NotificationMapper;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.service.ws.notification.BaseWSNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class PostMentionWSNotificationService extends BaseWSNotificationService implements MentionWSNotificationService<PostMention> {

    public PostMentionWSNotificationService(SimpMessagingTemplate simpMessagingTemplate, NotificationMapper notificationMapper) {
        super(simpMessagingTemplate, notificationMapper);
    }

    @Override
    public void broadcast(PostMention postMention) {
        if (postMention.getNotificationStatus() == NotificationStatus.READ) return;
        PostNotification postNotification = notificationMapper.toMentionNotification(postMention);
        simpMessagingTemplate.convertAndSend(MENTION_NOTIFICATION_DESTINATION + postNotification.getReceiverId(), postNotification);
        log.debug("Post mention notification successfully sent to mentioned user with id of {}", postNotification.getReceiverId());
    }
}
