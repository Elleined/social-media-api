package com.elleined.socialmediaapi.mapper.notification;

public interface NotificationMapper<T, DTO> {
    DTO toNotification(T t);
}
