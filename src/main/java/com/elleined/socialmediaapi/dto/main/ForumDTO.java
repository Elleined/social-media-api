package com.elleined.socialmediaapi.dto.main;

import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.model.main.Forum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class ForumDTO extends DTO {
    private String body;
    private Forum.Status status;
    private List<String> attachedPictures;
    private UserDTO creatorDTO;
}
