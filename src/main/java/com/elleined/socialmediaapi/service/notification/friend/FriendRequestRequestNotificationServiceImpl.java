package com.elleined.socialmediaapi.service.notification.friend;

import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FriendRequestRequestNotificationServiceImpl implements FriendRequestNotificationService {

    @Override
    public List<FriendRequest> getAllUnreadNotification(User currentUser) {
        return currentUser.getReceiveFriendRequest().stream()
                .filter(FriendRequest::isUnRead)
                .toList();
    }
}
