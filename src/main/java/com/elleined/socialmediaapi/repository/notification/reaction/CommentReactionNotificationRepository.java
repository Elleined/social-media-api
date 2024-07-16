package com.elleined.socialmediaapi.repository.notification.reaction;

import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.reaction.CommentReactionNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentReactionNotificationRepository extends ReactionNotificationRepository<CommentReactionNotification> {

    @Query("""
            SELECT crn
            FROM CommentReactionNotification crn
            WHERE crn.receiver = :currentUser
            AND crn.status = :status
            """)
    Page<CommentReactionNotification> findAll(@Param("currentUser") User currentUser,
                                              @Param("status") Notification.Status status,
                                              Pageable pageable);
}