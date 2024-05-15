package com.elleined.socialmediaapi.model.friend;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "tbl_friend_request")
@NoArgsConstructor
@Getter
@Setter
public class FriendRequest extends PrimaryKeyIdentity {

    @Enumerated(EnumType.STRING)
    @Column(
            name = "notification_status",
            nullable = false
    )
    private NotificationStatus notificationStatus;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User creator;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "requested_user_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User requestedUser;

    @Builder
    public FriendRequest(int id,
                         LocalDateTime createdAt,
                         LocalDateTime updatedAt,
                         NotificationStatus notificationStatus,
                         User creator,
                         User requestedUser) {
        super(id, createdAt, updatedAt);
        this.notificationStatus = notificationStatus;
        this.creator = creator;
        this.requestedUser = requestedUser;
    }

    public boolean isRead() {
        return this.notificationStatus == NotificationStatus.READ;
    }

    public boolean isUnRead() {
        return this.notificationStatus == NotificationStatus.UNREAD;
    }
}
