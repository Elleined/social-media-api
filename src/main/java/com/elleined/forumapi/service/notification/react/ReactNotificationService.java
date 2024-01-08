package com.elleined.forumapi.service.notification.react;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.react.Emoji;
import com.elleined.forumapi.model.react.React;
import com.elleined.forumapi.service.notification.NotificationService;

import java.util.List;

public interface ReactNotificationService<T extends React> extends NotificationService<T> {
    @Override
    List<T> getAllUnreadNotification(User currentUser);
}
