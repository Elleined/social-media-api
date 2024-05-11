package com.elleined.socialmediaapi.service.notification.friend;

import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.service.notification.NotificationService;

import java.util.List;

public interface FriendRequestNotificationService extends NotificationService<FriendRequest> {
    @Override
    List<FriendRequest> getAllUnreadNotification(User currentUser);
}
