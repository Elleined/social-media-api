package com.elleined.socialmediaapi.repository.notification.reaction;

import com.elleined.socialmediaapi.model.notification.reaction.ReactionNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ReactionNotificationRepository<T extends ReactionNotification> extends JpaRepository<T, Integer> {
}