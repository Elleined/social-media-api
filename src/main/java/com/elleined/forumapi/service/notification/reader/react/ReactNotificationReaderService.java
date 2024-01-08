package com.elleined.forumapi.service.notification.reader.react;

import com.elleined.forumapi.model.User;

public interface ReactNotificationReaderService {
    void readAll(User currentUser);
}
