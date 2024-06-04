package com.elleined.socialmediaapi.service.notification.friend;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.friend.FriendRequestNotificationMapper;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.friend.FriendRequestNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.friend.FriendRequestNotificationRepository;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FriendRequestNotificationServiceImpl implements FriendRequestNotificationService {
    private final UserRepository userRepository;

    private final FriendRequestNotificationRepository friendRequestNotificationRepository;
    private final FriendRequestNotificationMapper friendRequestNotificationMapper;

    @Override
    public List<FriendRequestNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return userRepository.findAllFriendRequestNotifications(currentUser, pageable).stream()
                .filter(notification -> notification.getStatus() == status)
                .toList();
    }

    @Override
    public FriendRequestNotification getById(int id) throws ResourceNotFoundException {
        return friendRequestNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mention notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(User currentUser, FriendRequestNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot read notification! because current user doesn't owned this notification!");

        notification.read();
        friendRequestNotificationRepository.save(notification);
    }

    @Override
    public FriendRequestNotification save(User creator, User receiver, String message, FriendRequest friendRequest) {
        FriendRequestNotification friendRequestNotification = friendRequestNotificationMapper.toEntity(creator, receiver, message, friendRequest);

        friendRequestNotificationRepository.save(friendRequestNotification);
        log.debug("Saving friend request notification success.");
        return friendRequestNotification;
    }
}
