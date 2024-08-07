package com.elleined.socialmediaapi.dto.story;

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
public class StoryDTO extends DTO {
    private String content;
    private String attachPicture;
    private UserDTO creatorDTO;
}
