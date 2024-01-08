package com.elleined.forumapi.service.notification.post.reader;

import com.elleined.forumapi.model.User;

public interface PostNotificationReader {
    void readAll(User currentUser);
}
