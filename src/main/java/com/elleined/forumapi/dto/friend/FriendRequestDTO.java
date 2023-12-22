package com.elleined.forumapi.dto.friend;

import com.elleined.forumapi.dto.UserDTO;
import com.elleined.forumapi.model.User;

import java.time.LocalDateTime;

public class FriendRequestDTO {
    private int id;
    private LocalDateTime createdAt;
    private UserDTO requestingUser;
    private UserDTO requestedUser;
}
