package com.elleined.socialmediaapi.model.notification;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class Notification extends PrimaryKeyIdentity {

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
    private User creator; // Current user

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "receiver_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User receiver; // Entity owner

    protected abstract String getMessage();

    public enum Status {
        READ,
        UN_READ
    }

    public void read() {
        this.setUpdatedAt(LocalDateTime.now());
        this.setStatus(Notification.Status.READ);
    }
}
