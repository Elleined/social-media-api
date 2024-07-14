package com.elleined.socialmediaapi.dto.notification.reaction;

import com.elleined.socialmediaapi.dto.story.StoryDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class StoryReactionNotificationDTO extends ReactionNotificationDTO {
    private StoryDTO storyDTO;
}
