package com.elleined.socialmediaapi.service.notification.post.reader;

import com.elleined.socialmediaapi.model.User;

public interface PostNotificationReader {
    void readAll(User currentUser);
}
