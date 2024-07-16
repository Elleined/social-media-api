package com.elleined.socialmediaapi.repository.notification.follow;

import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.follow.FollowerNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowerNotificationRepository extends JpaRepository<FollowerNotification, Integer> {
    @Query("""
            SELECT fn
            FROM FollowerNotification fn
            WHERE fn.receiver = :currentUser
            AND fn.status = :status
            """)
    Page<FollowerNotification> findAll(@Param("currentUser") User currentUser,
                                       @Param("status") Notification.Status status,
                                       Pageable pageable);
}