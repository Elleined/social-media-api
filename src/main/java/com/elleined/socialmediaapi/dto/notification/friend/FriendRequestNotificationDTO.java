package com.elleined.socialmediaapi.dto.notification.friend;

import com.elleined.socialmediaapi.dto.notification.NotificationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class FriendRequestNotificationDTO extends NotificationDTO {
    private int friendRequestId;
    private String message;
}
