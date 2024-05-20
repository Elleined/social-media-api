package com.elleined.socialmediaapi.dto.reaction;

import com.elleined.socialmediaapi.dto.DTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ReactionDTO extends DTO {
    private int creatorId;
    private Set<Integer> notificationIds;
    private int emojiId;
    private Set<Integer> postIds;
    private Set<Integer> commentIds;
    private Set<Integer> replyIds;
}
