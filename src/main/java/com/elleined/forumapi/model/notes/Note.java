package com.elleined.forumapi.model.notes;

import com.elleined.forumapi.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_user_note")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "note_id",
            nullable = false,
            unique = true,
            updatable = false
    )
    private  int id;

    @Column(
            name = "thought",
            nullable = false
    )
    private String thought;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @OneToOne(optional = false)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id",
            nullable = false
    )
    private User user;
}
