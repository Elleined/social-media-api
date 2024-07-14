package com.elleined.socialmediaapi.dto.notification.reaction;


import com.elleined.socialmediaapi.dto.note.NoteDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class NoteReactionNotificationDTO extends ReactionNotificationDTO {
    private NoteDTO noteDTO;
}
