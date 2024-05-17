package com.elleined.socialmediaapi.dto.friend;

import com.elleined.socialmediaapi.dto.DTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class FriendRequestDTO extends DTO {
    private Integer creatorId;
    private Integer requestedUserId;
    private Set<Integer> notificationIds;

    @Builder
    public FriendRequestDTO(int id,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt,
                            Integer creatorId,
                            Integer requestedUserId,
                            Set<Integer> notificationIds) {
        super(id, createdAt, updatedAt);
        this.creatorId = creatorId;
        this.requestedUserId = requestedUserId;
        this.notificationIds = notificationIds;
    }
}
