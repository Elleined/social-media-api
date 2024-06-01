package com.elleined.socialmediaapi.model.notification.reaction;

import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.react.Reaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class ReactionNotification extends Notification {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "reaction_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Reaction reaction;
}
