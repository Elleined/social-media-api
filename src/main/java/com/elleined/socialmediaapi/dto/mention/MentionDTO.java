package com.elleined.socialmediaapi.dto.mention;

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
public class MentionDTO extends DTO {
    private UserDTO creatorDTO;
    private UserDTO mentionedUserDTO;
}
