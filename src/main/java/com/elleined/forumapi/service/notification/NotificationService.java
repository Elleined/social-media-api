package com.elleined.forumapi.service.notification;

import com.elleined.forumapi.model.User;

import java.util.Collection;


public interface NotificationService<R> {

    Collection<R> getAllUnreadNotification(User currentUser);

    default int getNotificationCount(User currentUser) {
        return getAllUnreadNotification(currentUser).size();
    }
}
