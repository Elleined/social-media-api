package com.elleined.forumapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponse {
    private int id;
    private String message;
    private String respondentPicture;
    private int respondentId;
    private String type;
    private String notificationStatus;
    private int count;
    private String formattedDate;
    private String formattedTime;
    private int postId;
    private int commentId;
    private int receiverId;
}
