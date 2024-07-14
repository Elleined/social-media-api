package com.elleined.socialmediaapi.dto.notification.mention;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CommentMentionNotificationDTO extends MentionNotificationDTO {
    private CommentDTO commentDTO;
}
