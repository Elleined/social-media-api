package com.elleined.socialmediaapi.model.note;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "tbl_note")
@NoArgsConstructor
@Getter
@Setter
public class Note extends PrimaryKeyIdentity {

    @Column(
            name = "thought",
            nullable = false
    )
    private String thought;

    @OneToOne(optional = false)
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User creator;

    @Builder
    public Note(int id,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                String thought,
                User creator) {
        super(id, createdAt, updatedAt);
        this.thought = thought;
        this.creator = creator;
    }

    public boolean isExpired() {
        LocalDateTime noteCreation = this.getCreatedAt();
        LocalDateTime noteExpiration = noteCreation.plusDays(1);

        return LocalDateTime.now().isAfter(noteExpiration) ||
                LocalDateTime.now().equals(noteExpiration);
    }
}
