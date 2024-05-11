package com.elleined.socialmediaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Notification {
    private int id;
    private int creatorId;
    private int receiverId;
    private String message;
    private String notificationStatus;
}
