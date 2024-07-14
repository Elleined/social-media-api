package com.elleined.socialmediaapi.repository.notification.main;

import com.elleined.socialmediaapi.model.notification.main.ReplyNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyNotificationRepository extends NotificationRepository<ReplyNotification> {

    @Query("""
            SELECT rn
            FROM ReplyNotification rn
            WHERE rn.receiver = :currentUser
            """)
    Page<ReplyNotification> findAll(@Param("currentUser") User currentUser, Pageable pageable);
}