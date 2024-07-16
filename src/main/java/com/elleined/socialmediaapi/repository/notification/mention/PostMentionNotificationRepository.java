package com.elleined.socialmediaapi.repository.notification.mention;

import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.mention.PostMentionNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostMentionNotificationRepository extends MentionNotificationRepository<PostMentionNotification> {

    @Query("""
            SELECT pmn
            FROM PostMentionNotification pmn
            WHERE pmn.receiver = :currentUser
            AND pmn.status = :status
            """)
    Page<PostMentionNotification> findAll(@Param("currentUser") User currentUser,
                                          @Param("status") Notification.Status status,
                                          Pageable pageable);
}