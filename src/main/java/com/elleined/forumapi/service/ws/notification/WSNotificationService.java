package com.elleined.forumapi.service.ws.notification;

public interface WSNotificationService<T> {
    void broadcast(T t);
}
