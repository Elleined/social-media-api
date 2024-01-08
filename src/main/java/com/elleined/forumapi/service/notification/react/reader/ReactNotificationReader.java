package com.elleined.forumapi.service.notification.react.reader;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.react.Emoji;

public interface ReactNotificationReader {
    void readAll(User currentUser);
    void readAll(User currentUser, Emoji.Type type);
}
