package com.elleined.forumapi.service.notification.reader.friend;

import com.elleined.forumapi.model.User;

public interface FriendRequestNotificationReader {
    void readAll(User currentUser);
}
