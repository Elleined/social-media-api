package com.elleined.socialmediaapi.service.notification.mention;

import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.service.notification.NotificationService;

import java.util.List;

public interface MentionNotificationService<T extends Mention> extends NotificationService<T> {

    @Override
    List<T> getAllUnreadNotification(User currentUser);
}
