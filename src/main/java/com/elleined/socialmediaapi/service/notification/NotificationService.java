package com.elleined.socialmediaapi.service.notification;

import com.elleined.socialmediaapi.model.User;

import java.util.Collection;


public interface NotificationService<R> {

    Collection<R> getAllUnreadNotification(User currentUser);

    default int getNotificationCount(User currentUser) {
        return getAllUnreadNotification(currentUser).size();
    }
}
