package com.elleined.socialmediaapi.repository.notification.post;

import com.elleined.socialmediaapi.model.notification.post.SharedPostNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SharedPostNotificationRepository extends JpaRepository<SharedPostNotification, Integer> {

    @Query("""
            SELECT spn
            FROM SharedPostNotification spn
            WHERE spn.receiver = :currentUser
            """)
    Page<SharedPostNotification> findAll(@Param("currentUser") User currentUser, Pageable pageable);
}