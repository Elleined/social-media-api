package com.elleined.socialmediaapi.dto.notification.post;

import com.elleined.socialmediaapi.dto.main.PostDTO;
import com.elleined.socialmediaapi.dto.notification.NotificationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class SharedPostNotificationDTO extends NotificationDTO {
    private PostDTO postDTO;
}
