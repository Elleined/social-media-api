package com.elleined.socialmediaapi.repository.notification.reaction;

import com.elleined.socialmediaapi.model.notification.reaction.ReplyReactionNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyReactionNotificationRepository extends ReactionNotificationRepository<ReplyReactionNotification> {

    @Query("""
            SELECT rrn
            FROM ReplyReactionNotification rrn
            WHERE rrn.receiver = :currentUser
            """)
    Page<ReplyReactionNotification> findAll(@Param("currentUser") User currentUser, Pageable pageable);
}