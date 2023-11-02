package com.elleined.forumapi.service.notification.like;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.Like;
import com.elleined.forumapi.service.notification.NotificationService;

import java.util.List;

public interface LikeNotificationService<T extends Like> extends NotificationService<T> {
    @Override
    List<T> getAllUnreadNotification(User currentUser);
}
