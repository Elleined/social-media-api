package com.elleined.socialmediaapi.repository.notification.vote;

import com.elleined.socialmediaapi.model.notification.vote.VoteNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteNotificationRepository extends JpaRepository<VoteNotification, Integer> {
}