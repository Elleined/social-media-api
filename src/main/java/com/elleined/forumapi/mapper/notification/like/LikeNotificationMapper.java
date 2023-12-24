package com.elleined.forumapi.mapper.notification.like;

import com.elleined.forumapi.dto.notification.Notification;
import com.elleined.forumapi.mapper.notification.NotificationMapper;
import com.elleined.forumapi.model.like.Like;

public interface LikeNotificationMapper<T extends Like, DTO extends Notification> extends NotificationMapper<T, DTO> {
}
