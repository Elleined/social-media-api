package com.elleined.socialmediaapi.dto.main;

import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.model.main.Forum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@SuperBuilder(builderMethodName = "forumDtoBuilder")
@NoArgsConstructor
public abstract class ForumDTO extends DTO {
    private String body;
    private Forum.Status status;
    private String attachedPicture;
    private int creatorId;
    private Set<Integer> mentionIds;
    private Set<Integer> reactionIds;
    private Set<Integer> notificationIds;
}
