package com.elleined.forumapi.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Notification {
    private int id;
    private String message;
    private int receiverId;
    private String respondentPicture;
    private int respondentId;
    private String formattedDate;
    private String formattedTime;
    private String notificationStatus;
}
