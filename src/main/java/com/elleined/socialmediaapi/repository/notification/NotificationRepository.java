package com.elleined.socialmediaapi.repository.notification;

import com.elleined.socialmediaapi.model.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NotificationRepository<T extends Notification> extends JpaRepository<T, Integer> {
}