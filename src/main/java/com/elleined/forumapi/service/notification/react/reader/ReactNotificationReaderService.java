package com.elleined.forumapi.service.notification.react.reader;

import com.elleined.forumapi.model.User;

public interface ReactNotificationReaderService {
    void readAll(User currentUser);
}
