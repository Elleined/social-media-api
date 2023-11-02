package com.elleined.forumapi.service.notification;

import com.elleined.forumapi.model.User;

import java.util.Collection;
import java.util.List;

public interface NotificationService<R> {
    Collection<R> getAllNotification(User currentUser);
}
