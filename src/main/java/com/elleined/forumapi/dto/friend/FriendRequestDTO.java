package com.elleined.forumapi.dto.friend;

import com.elleined.forumapi.dto.UserDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FriendRequestDTO {
    private int id;
    private LocalDateTime createdAt;
    private UserDTO requestingUser;
    private UserDTO requestedUser;
    private String notificationStatus;
}
