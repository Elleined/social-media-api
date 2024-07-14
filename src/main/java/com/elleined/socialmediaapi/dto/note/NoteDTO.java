package com.elleined.socialmediaapi.dto.note;

import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.dto.user.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class NoteDTO extends DTO {
    private String thought;
    private UserDTO creatorDTO;
}
