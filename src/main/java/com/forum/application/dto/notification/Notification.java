package com.forum.application.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
