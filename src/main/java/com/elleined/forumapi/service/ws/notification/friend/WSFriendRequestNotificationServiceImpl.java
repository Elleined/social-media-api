package com.elleined.forumapi.service.ws.notification.friend;

import com.elleined.forumapi.model.friend.FriendRequest;
import com.elleined.forumapi.service.ws.notification.BaseWSNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WSFriendRequestNotificationServiceImpl extends BaseWSNotificationService implements WSFriendRequestNotificationService {


    protected WSFriendRequestNotificationServiceImpl(SimpMessagingTemplate simpMessagingTemplate) {
        super(simpMessagingTemplate);
    }

    @Override
    public void broadcastSendFriendRequest(FriendRequest friendRequest) {

    }

    @Override
    public void broadcastAcceptedFriendRequest(FriendRequest friendRequest) {

    }
}
