package com.elleined.socialmediaapi.repository.notification.friend;

import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.friend.FriendRequestNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRequestNotificationRepository extends JpaRepository<FriendRequestNotification, Integer> {
    @Query("""
            SELECT frn
            FROM FriendRequestNotification frn
            WHERE frn.receiver = :currentUser
            AND frn.status = :status
            """)
    Page<FriendRequestNotification> findAll(@Param("currentUser") User currentUser,
                                            @Param("status") Notification.Status status,
                                            Pageable pageable);
}