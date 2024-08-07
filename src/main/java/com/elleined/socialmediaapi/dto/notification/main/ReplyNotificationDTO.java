package com.elleined.socialmediaapi.dto.notification.main;

import com.elleined.socialmediaapi.dto.main.ReplyDTO;
import com.elleined.socialmediaapi.dto.notification.NotificationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ReplyNotificationDTO extends NotificationDTO {
    private ReplyDTO replyDTO;
}
