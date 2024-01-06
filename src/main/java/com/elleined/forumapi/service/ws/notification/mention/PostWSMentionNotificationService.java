package com.elleined.forumapi.service.ws.notification.mention;

import com.elleined.forumapi.dto.notification.PostNotification;
import com.elleined.forumapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.mention.PostMention;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostWSMentionNotificationService extends WSMentionBaseNotificationService implements WSMentionNotificationService<PostMention> {

    public PostWSMentionNotificationService(SimpMessagingTemplate simpMessagingTemplate, MentionNotificationMapper mentionNotificationMapper) {
        super(simpMessagingTemplate, mentionNotificationMapper);
    }

    @Override
    public void broadcast(PostMention postMention) {
        if (postMention.isRead()) return;
        PostNotification postNotification = mentionNotificationMapper.toNotification(postMention);
        simpMessagingTemplate.convertAndSend(MENTION_NOTIFICATION_DESTINATION + postNotification.getReceiverId(), postNotification);
        log.debug("Post mention notification successfully sent to mentioned user with id of {}", postNotification.getReceiverId());
    }
}
