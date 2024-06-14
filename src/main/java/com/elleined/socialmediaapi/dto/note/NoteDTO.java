package com.elleined.socialmediaapi.dto.note;

import com.elleined.socialmediaapi.dto.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class NoteDTO extends DTO {
    private String thought;
    private int creatorId;
    private Set<Integer> reactionIds;
}
