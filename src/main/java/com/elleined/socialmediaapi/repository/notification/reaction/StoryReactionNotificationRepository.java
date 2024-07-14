package com.elleined.socialmediaapi.repository.notification.reaction;

import com.elleined.socialmediaapi.model.notification.reaction.StoryReactionNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoryReactionNotificationRepository extends ReactionNotificationRepository<StoryReactionNotification> {

    @Query("""
            SELECT srn
            FROM StoryReactionNotification srn
            WHERE srn.receiver = :currentUser
            """)
    Page<StoryReactionNotification> findAll(@Param("currentUser") User currentUser, Pageable pageable);
}