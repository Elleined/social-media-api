package com.elleined.socialmediaapi.dto.notification.mention;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class StoryMentionNotificationDTO extends MentionNotificationDTO {
    private int storyId;
}
