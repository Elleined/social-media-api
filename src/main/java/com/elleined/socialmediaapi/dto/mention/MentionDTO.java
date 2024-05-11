package com.elleined.socialmediaapi.dto.mention;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.dto.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class MentionDTO extends DTO {
    private int creatorId;
    private int mentionedUserId;
    private NotificationStatus notificationStatus;
    private Set<Integer> postIds;
    private Set<Integer> commentIds;
    private Set<Integer> replieIds;
}
