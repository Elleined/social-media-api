package com.elleined.socialmediaapi.repository.notification.mention;

import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.mention.ReplyMentionNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyMentionNotificationRepository extends MentionNotificationRepository<ReplyMentionNotification> {

    @Query("""
            SELECT rmn
            FROM ReplyMentionNotification rmn
            WHERE rmn.receiver = :currentUser
            AND rmn.status = :status
            """)
    Page<ReplyMentionNotification> findAll(@Param("currentUser") User currentUser,
                                           @Param("status") Notification.Status status,
                                           Pageable pageable);
}