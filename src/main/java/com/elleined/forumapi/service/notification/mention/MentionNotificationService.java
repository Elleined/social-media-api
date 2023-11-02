package com.elleined.forumapi.service.notification.mention;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.Mention;
import com.elleined.forumapi.service.notification.NotificationService;

import java.util.List;

public interface MentionNotificationService<T extends Mention> extends NotificationService<T> {

    @Override
    List<T> getAllUnreadNotification(User currentUser);
}
