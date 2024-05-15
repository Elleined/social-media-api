package com.elleined.socialmediaapi.dto.notification;

import com.elleined.socialmediaapi.dto.DTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO extends DTO {
    private int creatorId;
    private int receiverId;
    private String message;
    private String notificationStatus;

    @Builder
    public NotificationDTO(int id,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt,
                           int creatorId,
                           int receiverId,
                           String message,
                           String notificationStatus) {
        super(id, createdAt, updatedAt);
        this.creatorId = creatorId;
        this.receiverId = receiverId;
        this.message = message;
        this.notificationStatus = notificationStatus;
    }
}
