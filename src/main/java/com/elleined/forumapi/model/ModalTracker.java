package com.elleined.forumapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_modal_tracker")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ModalTracker {

    @Id
    @Column(
            name = "receiver_user_id",
            unique = true,
            nullable = false,
            updatable = false
    )
    private int receiverId;

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

    public enum Type {
        POST,
        COMMENT,
        REPLY
    }
}
