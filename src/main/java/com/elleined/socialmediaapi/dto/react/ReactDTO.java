package com.elleined.socialmediaapi.dto.react;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.dto.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class ReactDTO extends DTO {
    private int creatorId;
    private NotificationStatus notificationStatus;
    private Emoji.Type emojiType;
    private Set<Integer> postIds;
    private Set<Integer> commentIds;
    private Set<Integer> replieIds;
}
