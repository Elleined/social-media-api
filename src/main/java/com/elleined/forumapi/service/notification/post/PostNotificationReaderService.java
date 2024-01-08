package com.elleined.forumapi.service.notification.post;

import com.elleined.forumapi.model.User;

public interface PostNotificationReaderService {
    void readAll(User currentUser);
}
