package com.elleined.socialmediaapi.dto.friend;

import com.elleined.socialmediaapi.dto.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class FriendRequestDTO extends DTO {
    private Integer creatorId;
    private Integer requestedUserId;
}
