package com.elleined.socialmediaapi.model.notification.follow;

import com.elleined.socialmediaapi.model.notification.Notification;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(region = "followerNotificationCache", usage = CacheConcurrencyStrategy.READ_WRITE)

@Entity
@Table(name = "tbl_follower_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class FollowerNotification extends Notification {

    @Override
    public String getMessage() {
        return STR."\{this.getCreator().getName()} started following you";
    }
}
