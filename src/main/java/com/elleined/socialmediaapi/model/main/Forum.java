package com.elleined.socialmediaapi.model.main;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder(builderMethodName = "forumBuilder")
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


    public boolean isActive() {
        return this.getStatus() == Status.ACTIVE;
    }

    public boolean isInactive() {
        return this.getStatus() == Status.INACTIVE;
    }

    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
