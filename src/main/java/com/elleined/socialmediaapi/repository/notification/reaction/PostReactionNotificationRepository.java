package com.elleined.socialmediaapi.repository.notification.reaction;

import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.reaction.PostReactionNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostReactionNotificationRepository extends ReactionNotificationRepository<PostReactionNotification> {

    @Query("""
            SELECT prn
            FROM PostReactionNotification prn
            WHERE prn.receiver = :currentUser
            AND prn.status = :status
            """)
    Page<PostReactionNotification> findAll(@Param("currentUser") User currentUser,
                                           @Param("status") Notification.Status status,
                                           Pageable pageable);
}