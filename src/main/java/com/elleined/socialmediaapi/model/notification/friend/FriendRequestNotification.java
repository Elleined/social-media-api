package com.elleined.socialmediaapi.model.notification.friend;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.notification.Notification;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(region = "friendRequestNotificationCache", usage = CacheConcurrencyStrategy.READ_WRITE)

@Entity
@Table(name = "tbl_friend_request_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class FriendRequestNotification extends Notification {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "friend_request_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private FriendRequest friendRequest;

    @Transient
    private String message;

    @Override
    public String getMessage() {
        return message;
    }
}
