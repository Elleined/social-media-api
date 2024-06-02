package com.elleined.socialmediaapi.repository.notification.follow;

import com.elleined.socialmediaapi.model.notification.follow.FollowerNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerNotificationRepository extends JpaRepository<FollowerNotification, Integer> {
}