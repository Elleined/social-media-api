package com.elleined.socialmediaapi.model.notification.reaction;

import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.reaction.Emoji;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_note_reaction_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class NoteReactionNotification extends ReactionNotification {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "note_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Note note;

    @Override
    public String getMessage() {
        if (this.getReaction().getEmoji().getType().equals(Emoji.Type.HEART.name()))
            return STR."\{this.getCreator().getName()} loves to your note: \"\{this.getNote().getThought()}\" ";

        return STR."\{this.getCreator().getName()} reacted to your note: \"\{this.getNote().getThought()}\" ";
    }
}
