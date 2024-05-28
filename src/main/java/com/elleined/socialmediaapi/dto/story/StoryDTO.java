package com.elleined.socialmediaapi.dto.story;

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
public class StoryDTO extends DTO {
    private String content;
    private String attachPicture;
    private int creatorId;
    private Set<Integer> mentionIds;
    private Set<Integer> reactionIds;
}
