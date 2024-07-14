package com.elleined.socialmediaapi.service.notification;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface NotificationService<T extends Notification> {
    Page<T> getAll(User currentUser, Notification.Status status, Pageable pageable);
    T getById(int id) throws ResourceNotFoundException;
    void read(User currentUser, T notification);
    void unread(User currentUser, T notification);

    default void readAll(User currentUser, Collection<T> notifications) {
        notifications.forEach(notification -> this.read(currentUser, notification));
    }

    default void unreadAll(User currentUser, Collection<T> notifications) {
        notifications.forEach(notification -> this.unread(currentUser, notification));
    }
}
