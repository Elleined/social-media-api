package com.elleined.forumapi.service.notification.friend.reader;

import com.elleined.forumapi.model.User;

public interface FriendRequestNotificationReader {
    void readAll(User currentUser);
}
