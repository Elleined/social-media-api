package com.elleined.socialmediaapi.model.main;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class Forum extends PrimaryKeyIdentity {

    @Column(
            name = "body",
            nullable = false
    )
    private String body;

    @Column(
            name = "status",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "attached_picture")
    private String attachedPicture;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User creator;

    @Column(
            name = "notification_status",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    @Builder
    public Forum(int id,
                 LocalDateTime createdAt,
                 LocalDateTime updatedAt,
                 String body,
                 Status status,
                 String attachedPicture,
                 User creator,
                 NotificationStatus notificationStatus) {
        super(id, createdAt, updatedAt);
        this.body = body;
        this.status = status;
        this.attachedPicture = attachedPicture;
        this.creator = creator;
        this.notificationStatus = notificationStatus;
    }

    public boolean isActive() {
        return this.getStatus() == Status.ACTIVE;
    }

    public boolean isInactive() {
        return this.getStatus() == Status.INACTIVE;
    }

    public boolean isRead() {
        return this.getNotificationStatus() == NotificationStatus.READ;
    }

    public boolean isUnread() {
        return this.getNotificationStatus() == NotificationStatus.UNREAD;
    }
}
