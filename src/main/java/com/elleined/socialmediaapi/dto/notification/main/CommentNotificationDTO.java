package com.elleined.socialmediaapi.dto.notification.main;

import com.elleined.socialmediaapi.dto.notification.NotificationDTO;
import com.elleined.socialmediaapi.model.notification.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CommentNotificationDTO extends NotificationDTO {
    private int commentId;




}
