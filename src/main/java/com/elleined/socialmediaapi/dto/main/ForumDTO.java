package com.elleined.socialmediaapi.dto.main;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.Status;
import com.elleined.socialmediaapi.dto.DTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ForumDTO extends DTO {
    private String body;
    private Status status;
    private String attachedPicture;
    private int creatorId;
    private NotificationStatus notificationStatus;

    @Builder
    public ForumDTO(int id, LocalDateTime createdAt, LocalDateTime updatedAt, String body, Status status, String attachedPicture, int creatorId, NotificationStatus notificationStatus) {
        super(id, createdAt, updatedAt);
        this.body = body;
        this.status = status;
        this.attachedPicture = attachedPicture;
        this.creatorId = creatorId;
        this.notificationStatus = notificationStatus;
    }
}
