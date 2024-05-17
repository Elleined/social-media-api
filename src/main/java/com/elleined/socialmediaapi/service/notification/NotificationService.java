package com.elleined.socialmediaapi.service.notification;

import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.user.User;
import org.w3c.dom.Notation;

import java.util.Collection;
import java.util.List;


public interface NotificationService {
    List<Notification> getAllNotification(User currentUser);
    List<Notification> getAllNotification(User currentUser, Notification.Status status);

    void read();
    void readAll();
}
