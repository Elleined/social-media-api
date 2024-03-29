package com.elleined.socialmediaapi.model.note;

import com.elleined.socialmediaapi.model.User;
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
            unique = true,
            nullable = false,
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
            nullable = false,
            updatable = false,
            unique = true
    )
    private User user;

    public boolean isExpired() {
        LocalDateTime noteCreation = this.getCreatedAt();
        LocalDateTime noteExpiration = noteCreation.plusDays(1);

        return LocalDateTime.now().isAfter(noteExpiration) ||
                LocalDateTime.now().equals(noteExpiration);
    }
}
