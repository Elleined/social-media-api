package com.elleined.socialmediaapi.dto.mention;

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
@SuperBuilder(builderMethodName = "mentionDtoBuilder")
@NoArgsConstructor
public class MentionDTO extends DTO {
    private int creatorId;
    private int mentionedUserId;
    private Set<Integer> notificationIds;
    private Set<Integer> postIds;
    private Set<Integer> commentIds;
    private Set<Integer> replyIds;
}
