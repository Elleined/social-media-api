package com.elleined.forumapi.service.ws.notification.friend;

import com.elleined.forumapi.model.friend.FriendRequest;

public interface WSFriendRequestNotificationService {

    void broadcastSendFriendRequest(FriendRequest friendRequest);
    void broadcastAcceptedFriendRequest(FriendRequest friendRequest);
}
