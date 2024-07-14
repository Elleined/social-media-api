package com.elleined.socialmediaapi.service.notification.friend;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.notification.friend.FriendRequestNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.NotificationService;

public interface FriendRequestNotificationService extends NotificationService<FriendRequestNotification> {

    FriendRequestNotification save(User creator,
                                   User receiver,
                                   String message,
                                   FriendRequest friendRequest);
}
