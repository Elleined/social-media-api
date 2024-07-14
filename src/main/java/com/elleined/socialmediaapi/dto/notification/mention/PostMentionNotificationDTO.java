package com.elleined.socialmediaapi.dto.notification.mention;

import com.elleined.socialmediaapi.dto.main.PostDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PostMentionNotificationDTO extends MentionNotificationDTO {
    private PostDTO postDTO;
}
