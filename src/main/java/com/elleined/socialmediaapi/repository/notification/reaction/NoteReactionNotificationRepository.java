package com.elleined.socialmediaapi.repository.notification.reaction;

import com.elleined.socialmediaapi.model.notification.reaction.NoteReactionNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoteReactionNotificationRepository extends ReactionNotificationRepository<NoteReactionNotification> {

    @Query("""
            SELECT nrn
            FROM NoteReactionNotification nrn
            WHERE nrn.receiver = :currentUser
            """)
    Page<NoteReactionNotification> findAll(@Param("currentUser") User currentUser, Pageable pageable);
}