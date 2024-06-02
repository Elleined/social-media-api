package com.elleined.socialmediaapi.service.notification.post;

import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.notification.post.SharedPostNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.NotificationService;

public interface SharedPostNotificationService extends NotificationService<SharedPostNotification> {
    SharedPostNotification save(User currentUser, Post post);
}
