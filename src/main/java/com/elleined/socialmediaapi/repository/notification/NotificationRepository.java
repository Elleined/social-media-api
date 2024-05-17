package com.elleined.socialmediaapi.repository.notification;

import com.elleined.socialmediaapi.model.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}