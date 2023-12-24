package com.elleined.forumapi.mapper.notification;

public interface NotificationMapper<T, DTO> {
    DTO toNotification(T t);
}
