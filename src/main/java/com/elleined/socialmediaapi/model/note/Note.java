package com.elleined.socialmediaapi.model.note;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "tbl_note")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
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


    @ManyToMany
    @JoinTable(
            name = "tbl_note_reaction",
            joinColumns = @JoinColumn(
                    name = "note_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "reaction_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<Reaction> reactions;

    public boolean isExpired() {
        LocalDateTime noteCreation = this.getCreatedAt();
        LocalDateTime noteExpiration = noteCreation.plusDays(1);

        return LocalDateTime.now().isAfter(noteExpiration) ||
                LocalDateTime.now().equals(noteExpiration);
    }

    public boolean notOwned(Reaction reaction) {
        return !this.getReactions().contains(reaction);
    }
}
