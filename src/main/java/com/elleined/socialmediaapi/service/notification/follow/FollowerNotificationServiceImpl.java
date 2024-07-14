package com.elleined.socialmediaapi.service.notification.follow;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.follow.FollowerNotificationMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.follow.FollowerNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.follow.FollowerNotificationRepository;
import com.elleined.socialmediaapi.repository.user.UserRepository;
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
public class FollowerNotificationServiceImpl implements FollowerNotificationService {
    private final UserRepository userRepository;

    private final FollowerNotificationRepository followerNotificationRepository;
    private final FollowerNotificationMapper followerNotificationMapper;

    @Override
    public Page<FollowerNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return userRepository.findAllFollowerNotifications(currentUser, pageable).stream()
                .filter(notification -> notification.getStatus() == status)
                .toList();
    }

    @Override
    public FollowerNotification getById(int id) throws ResourceNotFoundException {
        return followerNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mention notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(User currentUser, FollowerNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot read notification! because current user doesn't owned this notification!");

        notification.read();
        followerNotificationRepository.save(notification);
    }

    @Override
    public FollowerNotification save(User currentUser, User receiver) {
        FollowerNotification followerNotification = followerNotificationMapper.toEntity(currentUser, receiver);

        followerNotificationRepository.save(followerNotification);
        log.debug("Saving follower notification success");
        return followerNotification;
    }
}
