package com.elleined.forumapi.service.notification;

import com.elleined.forumapi.model.User;

import java.util.Collection;

public interface NotificationService<R> {
    Collection<R> getAllUnreadNotification(User currentUser);
    int getNotificationCount(User currentUser);
}
