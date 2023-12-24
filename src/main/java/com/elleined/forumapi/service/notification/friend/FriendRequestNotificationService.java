package com.elleined.forumapi.service.notification.friend;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.friend.FriendRequest;
import com.elleined.forumapi.service.notification.NotificationService;

import java.util.List;

public interface FriendRequestNotificationService extends NotificationService<FriendRequest> {
    @Override
    List<FriendRequest> getAllUnreadNotification(User currentUser);
}
