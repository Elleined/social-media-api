package com.elleined.socialmediaapi.model.main;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
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
