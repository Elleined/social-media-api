package com.elleined.socialmediaapi.service.notification.friend;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.friend.FriendRequestNotificationMapper;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.friend.FriendRequestNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.friend.FriendRequestNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class FriendRequestNotificationServiceImpl implements FriendRequestNotificationService {
    private final FriendRequestNotificationRepository friendRequestNotificationRepository;
    private final FriendRequestNotificationMapper friendRequestNotificationMapper;

    @Override
    public Page<FriendRequestNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return friendRequestNotificationRepository.findAll(currentUser, status, pageable);
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
    public void unread(User currentUser, FriendRequestNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot unread notification! because current user doesn't owned this notification!");

        notification.unread();
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
