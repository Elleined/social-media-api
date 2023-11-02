package com.elleined.forumapi.service.notification;

import com.elleined.forumapi.dto.notification.Notification;
import com.elleined.forumapi.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService<Notification> {

    @Override
    public Collection<Notification> getAllUnreadNotification(User currentUser) {

    }

    @Override
    public int getNotificationCount(User currentUser) {
        return getAllUnreadNotification(currentUser).size();
    }
}
