package com.elleined.socialmediaapi.model.friend;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_user_friend_request")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            unique = true,
            nullable = false,
            updatable = false
    )
    private int id;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "requested_user_id",
            referencedColumnName = "user_id",
            nullable = false,
            updatable = false
    )
    private User requestedUser;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "requesting_user_id",
            referencedColumnName = "user_id",
            nullable = false,
            updatable = false
    )
    private User requestingUser;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "notification_status",
            nullable = false
    )
    private NotificationStatus notificationStatus;

    public boolean isRead() {
        return this.notificationStatus == NotificationStatus.READ;
    }

    public boolean isUnRead() {
        return this.notificationStatus == NotificationStatus.UNREAD;
    }
}
