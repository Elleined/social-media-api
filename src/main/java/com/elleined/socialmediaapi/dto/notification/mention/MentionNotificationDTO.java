package com.elleined.socialmediaapi.dto.notification.mention;

import com.elleined.socialmediaapi.dto.notification.NotificationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class MentionNotificationDTO extends NotificationDTO {
    private int mentionId;
}
