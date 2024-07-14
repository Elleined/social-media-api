package com.elleined.socialmediaapi.repository.notification.mention;

import com.elleined.socialmediaapi.model.notification.mention.StoryMentionNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoryMentionNotificationRepository extends MentionNotificationRepository<StoryMentionNotification> {

    @Query("""
            SELECT smn
            FROM StoryMentionNotification smn
            WHERE smn.receiver = :currentUser
            """)
    Page<StoryMentionNotification> findAll(@Param("currentUser") User currentUser, Pageable pageable);
}