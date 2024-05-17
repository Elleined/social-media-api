package com.elleined.socialmediaapi.model.friend;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "tbl_friend_request")
@NoArgsConstructor
@Getter
@Setter
public class FriendRequest extends PrimaryKeyIdentity {

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

    @ManyToMany(mappedBy = "friendRequests")
    private Set<Notification> notifications;

    @Builder
    public FriendRequest(int id,
                         LocalDateTime createdAt,
                         LocalDateTime updatedAt,
                         User creator,
                         User requestedUser,
                         Set<Notification> notifications) {
        super(id, createdAt, updatedAt);
        this.creator = creator;
        this.requestedUser = requestedUser;
        this.notifications = notifications;
    }
}
