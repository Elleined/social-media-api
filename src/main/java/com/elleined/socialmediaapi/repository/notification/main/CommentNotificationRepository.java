package com.elleined.socialmediaapi.repository.notification.main;

import com.elleined.socialmediaapi.model.notification.main.CommentNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentNotificationRepository extends NotificationRepository<CommentNotification> {

    @Query("""
            SELECT cn
            FROM CommentNotification cn
            WHERE cn.receiver = :currentUser
            """)
    Page<CommentNotification> findAll(@Param("currentUser") User currentUser, Pageable pageable);
}