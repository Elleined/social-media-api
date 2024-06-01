package com.elleined.socialmediaapi.dto.notification.mention;

import com.elleined.socialmediaapi.dto.notification.main.ReplyNotificationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ReplyMentionNotificationDTO extends ReplyNotificationDTO {
    private int replyId;
}
