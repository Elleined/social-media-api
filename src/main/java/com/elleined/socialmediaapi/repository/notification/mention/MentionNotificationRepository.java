package com.elleined.socialmediaapi.repository.notification.mention;

import com.elleined.socialmediaapi.model.notification.mention.MentionNotification;
import com.elleined.socialmediaapi.repository.notification.NotificationRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MentionNotificationRepository<T extends MentionNotification> extends NotificationRepository<T> {
}