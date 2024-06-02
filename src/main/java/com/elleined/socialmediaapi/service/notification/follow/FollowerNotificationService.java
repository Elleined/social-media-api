package com.elleined.socialmediaapi.service.notification.follow;

import com.elleined.socialmediaapi.model.notification.follow.FollowerNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.NotificationService;

public interface FollowerNotificationService extends NotificationService<FollowerNotification> {
    FollowerNotification save(User currentUser, User receiver);
}
