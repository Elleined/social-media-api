package com.elleined.socialmediaapi.dto.notification;

import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.model.notification.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@SuperBuilder(builderMethodName = "notificationDtoBuilder")
@NoArgsConstructor
public class NotificationDTO extends DTO {
    private String message;
    private Notification.Status status;
    private Set<Integer> postIds;
    private Set<Integer> commentIds;
    private Set<Integer> replyIds;
    private Set<Integer> reactIds;
    private Set<Integer> mentionIds;
    private Set<Integer> friendRequestIds;
}
