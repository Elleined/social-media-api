package com.elleined.forumapi.service.like.notification;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.Like;

import java.util.List;

public interface LikeNotificationService<T extends Like> {
    List<T> getAllUnreadLikeNotification(User currentUser);
}
