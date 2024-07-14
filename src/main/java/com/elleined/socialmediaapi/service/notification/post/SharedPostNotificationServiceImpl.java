package com.elleined.socialmediaapi.service.notification.post;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.post.SharedPostNotificationMapper;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.post.SharedPostNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.post.SharedPostNotificationRepository;
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
public class SharedPostNotificationServiceImpl implements SharedPostNotificationService {
    private final UserRepository userRepository;

    private final SharedPostNotificationRepository sharedPostNotificationRepository;
    private final SharedPostNotificationMapper sharedPostNotificationMapper;

    @Override
    public Page<SharedPostNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return userRepository.findAllSharedPostNotifications(currentUser, pageable).stream()
                .filter(notification -> notification.getStatus() == status)
                .toList();
    }

    @Override
    public SharedPostNotification getById(int id) throws ResourceNotFoundException {
        return sharedPostNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mention notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(User currentUser, SharedPostNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot read notification! because current user doesn't owned this notification!");

        notification.read();
        sharedPostNotificationRepository.save(notification);
    }

    @Override
    public SharedPostNotification save(User currentUser, Post post) {
        SharedPostNotification sharedPostNotification = sharedPostNotificationMapper.toEntity(currentUser, post);

        sharedPostNotificationRepository.save(sharedPostNotification);
        log.debug("Saving shared post notification success");
        return sharedPostNotification;
    }
}
