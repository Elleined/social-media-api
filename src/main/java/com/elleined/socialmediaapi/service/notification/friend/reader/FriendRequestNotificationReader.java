package com.elleined.socialmediaapi.service.notification.friend.reader;

import com.elleined.socialmediaapi.model.User;

public interface FriendRequestNotificationReader {
    void readAll(User currentUser);
}
