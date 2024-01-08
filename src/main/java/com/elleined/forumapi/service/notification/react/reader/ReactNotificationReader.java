package com.elleined.forumapi.service.notification.react.reader;

import com.elleined.forumapi.model.User;

public interface ReactNotificationReader {
    void readAll(User currentUser);
}
