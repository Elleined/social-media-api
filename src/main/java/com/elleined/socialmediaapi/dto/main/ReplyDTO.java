package com.elleined.socialmediaapi.dto.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ReplyDTO extends ForumDTO {
    private int postId; // this field is not present in entity
    private int commentId;
}
