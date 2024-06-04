package com.elleined.socialmediaapi.repository.notification.friend;

import com.elleined.socialmediaapi.model.notification.friend.FriendRequestNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestNotificationRepository extends JpaRepository<FriendRequestNotification, Integer> {
}