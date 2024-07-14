package com.elleined.socialmediaapi.repository.notification.mention;

import com.elleined.socialmediaapi.model.notification.mention.CommentMentionNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentMentionNotificationRepository extends MentionNotificationRepository<CommentMentionNotification> {

    @Query("""
            SELECT cmn
            FROM CommentMentionNotification cmn
            WHERE cmn.receiver = :currentUser
            """)
    Page<CommentMentionNotification> findAll(@Param("currentUser") User currentUser, Pageable pageable);
}