package com.elleined.socialmediaapi.dto.notification;

import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.model.notification.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class NotificationDTO extends DTO {
    private String message;
    private Notification.Status status;
    private UserDTO creatorDTO;
    private UserDTO receiverDTO;

    public boolean isRead() {
        return status == Notification.Status.READ;
    }

    public int getReceiverId() {
        return this.getReceiverDTO().getId();
    }
}
