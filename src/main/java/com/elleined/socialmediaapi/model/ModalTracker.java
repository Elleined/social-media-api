package com.elleined.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_modal_tracker")
@NoArgsConstructor
@Getter
@Setter
public class ModalTracker extends PrimaryKeyIdentity {

    @Column(
            name = "modal_type",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(
            name = "associated_id_opened",
            nullable = false,
            updatable = false
    )
    private int associatedTypeIdOpened;

    @Builder
    public ModalTracker(int id,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt,
                        Type type,
                        int associatedTypeIdOpened) {
        super(id, createdAt, updatedAt);
        this.type = type;
        this.associatedTypeIdOpened = associatedTypeIdOpened;
    }

    public enum Type {
        POST,
        COMMENT,
        REPLY
    }
}
