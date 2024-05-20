package com.elleined.socialmediaapi.dto.friend;

import com.elleined.socialmediaapi.dto.DTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@SuperBuilder(builderMethodName = "friendRequestDtoBuilder")
@NoArgsConstructor
public class FriendRequestDTO extends DTO {
    private Integer creatorId;
    private Integer requestedUserId;
    private Set<Integer> notificationIds;
}
