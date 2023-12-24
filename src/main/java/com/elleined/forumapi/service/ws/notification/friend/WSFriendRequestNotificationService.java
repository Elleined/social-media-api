package com.elleined.forumapi.service.ws.notification.friend;

public interface WSFriendRequestNotificationService {

    void broadcastSendFriendRequest();
    void broadcastAcceptedFriendRequest();
}
