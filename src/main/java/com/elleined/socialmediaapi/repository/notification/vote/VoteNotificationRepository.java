package com.elleined.socialmediaapi.repository.notification.vote;

import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.vote.VoteNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteNotificationRepository extends JpaRepository<VoteNotification, Integer> {

    @Query("SELECT vn FROM VoteNotification vn WHERE vn.receiver = :currentUser AND vn.status = :status")
    Page<VoteNotification> findAll(@Param("currentUser") User currentUser,
                                   @Param("status") Notification.Status status,
                                   Pageable pageable);
}