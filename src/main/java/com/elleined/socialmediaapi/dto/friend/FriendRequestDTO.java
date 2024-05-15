package com.elleined.socialmediaapi.dto.friend;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.dto.DTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class FriendRequestDTO extends DTO {
    private Integer creatorId;
    private Integer requestedUserId;
    private NotificationStatus notificationStatus;

    @Builder
    public FriendRequestDTO(int id,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt,
                            Integer creatorId,
                            Integer requestedUserId,
                            NotificationStatus notificationStatus) {
        super(id, createdAt, updatedAt);
        this.creatorId = creatorId;
        this.requestedUserId = requestedUserId;
        this.notificationStatus = notificationStatus;
    }
}
