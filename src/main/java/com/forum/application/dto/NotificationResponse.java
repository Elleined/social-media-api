package com.forum.application.dto;

import com.forum.application.model.ModalTracker;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponse {
    private int id;
    private String message;
    private String respondentPicture;
    private int respondentId;
    private ModalTracker.Type type;
    private String notificationStatus;
    private int count;
    private String formattedDate;
    private String formattedTime;
    private int postId;
    private int commentId;
}
