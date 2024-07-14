package com.elleined.socialmediaapi.dto.notification.mention;

import com.elleined.socialmediaapi.dto.main.ReplyDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ReplyMentionNotificationDTO extends MentionNotificationDTO {
    private ReplyDTO replyDTO;
}
