package com.elleined.forumapi.service.notification;

import com.elleined.forumapi.model.User;

import java.util.Collection;


public interface NotificationService<R> {

    /**
     * Does not include inactive entity
     * Does not include blocked user
     * Does not include user that blocked you
     * Does not include read status
     * **/
    Collection<R> getAllUnreadNotification(User currentUser);

    default int getNotificationCount(User currentUser) {
        return getAllUnreadNotification(currentUser).size();
    }
}
