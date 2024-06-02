package com.elleined.socialmediaapi.repository.notification.post;

import com.elleined.socialmediaapi.model.notification.post.SharedPostNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharedPostNotificationRepository extends JpaRepository<SharedPostNotification, Integer> {
}