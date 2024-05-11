package com.elleined.socialmediaapi.service.notification.react;

import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.react.React;
import com.elleined.socialmediaapi.service.notification.NotificationService;

import java.util.List;

public interface ReactNotificationService<T extends React> extends NotificationService<T> {
    @Override
    List<T> getAllUnreadNotification(User currentUser);
}
