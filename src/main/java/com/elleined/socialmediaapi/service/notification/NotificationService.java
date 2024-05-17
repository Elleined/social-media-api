package com.elleined.socialmediaapi.service.notification;

import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.user.User;

import java.util.List;
import java.util.Set;


public interface NotificationService {
    List<Notification> getAllById(Set<Integer> ids);
    List<Notification> getAllNotification(User currentUser);
    List<Notification> getAllNotification(User currentUser, Notification.Status status);

    default List<Notification> getAllUnreadNotification(User currentUser) {
        return this.getAllNotification(currentUser, Notification.Status.UNREAD);
    }
}
