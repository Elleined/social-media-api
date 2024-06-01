package com.elleined.socialmediaapi.model.notification.mention;

import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.notification.Notification;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class MentionNotification extends Notification {
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "mention_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Mention mention;
}
