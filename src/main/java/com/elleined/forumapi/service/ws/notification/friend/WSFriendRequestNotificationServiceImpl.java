package com.elleined.forumapi.service.ws.notification.friend;

import com.elleined.forumapi.dto.notification.FriendRequestNotification;
import com.elleined.forumapi.mapper.FriendRequestMapper;
import com.elleined.forumapi.mapper.notification.friend.FriendRequestNotificationMapper;
import com.elleined.forumapi.model.friend.FriendRequest;
import com.elleined.forumapi.service.ws.notification.BaseWSNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WSFriendRequestNotificationServiceImpl extends BaseWSNotificationService implements WSFriendRequestNotificationService {

    private final FriendRequestNotificationMapper friendRequestNotificationMapper;

    protected WSFriendRequestNotificationServiceImpl(SimpMessagingTemplate simpMessagingTemplate, FriendRequestMapper friendRequestMapper, FriendRequestNotificationMapper friendRequestNotificationMapper) {
        super(simpMessagingTemplate);
        this.friendRequestNotificationMapper = friendRequestNotificationMapper;
    }

    @Override
    public void broadcastSendFriendRequest(FriendRequest friendRequest) {
        if (friendRequest.isRead()) return;
        FriendRequestNotification friendRequestNotification = friendRequestNotificationMapper.toSendNotification(friendRequest);
        int requestedUserId = friendRequest.getRequestedUser().getId();
        final String destination = "/notification/friend-requests/" + requestedUserId;
        simpMessagingTemplate.convertAndSend(destination, friendRequestNotification);
        log.debug("Send friend request successfully sent to requested user with id of {}", requestedUserId);
    }

    @Override
    public void broadcastAcceptedFriendRequest(FriendRequest friendRequest) {
        FriendRequestNotification friendRequestNotification = friendRequestNotificationMapper.toAcceptedNotification(friendRequest);
        int requestingUserId = friendRequest.getRequestingUser().getId();
        final String destination = "/notification/friend-requests/" + requestingUserId;
        simpMessagingTemplate.convertAndSend(destination, friendRequestNotification);
        log.debug("Accepted friend request successfully sent to requesting user with id of {}", requestingUserId);
    }
}
