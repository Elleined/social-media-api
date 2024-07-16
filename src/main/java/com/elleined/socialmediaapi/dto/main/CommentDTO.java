package com.elleined.socialmediaapi.dto.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CommentDTO extends ForumDTO {
    private PostDTO postDTO;
}
