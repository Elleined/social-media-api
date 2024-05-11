package com.elleined.socialmediaapi.service.notification.friend.reader;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.repository.friend.FriendRequestRepository;
import com.elleined.socialmediaapi.service.notification.friend.FriendRequestNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FriendRequestNotificationReaderImpl implements FriendRequestNotificationReader {
    private final FriendRequestNotificationService friendRequestNotificationService;
    private final FriendRequestRepository friendRequestRepository;

    @Override
    public void readAll(User currentUser) {
        List<FriendRequest> friendRequests = friendRequestNotificationService.getAllUnreadNotification(currentUser);
        friendRequests.forEach(friendRequest -> friendRequest.setNotificationStatus(NotificationStatus.READ));
        friendRequestRepository.saveAll(friendRequests);
        log.debug("Reading all receive friend requests for current user with id of {} success", currentUser.getId());
    }
}
