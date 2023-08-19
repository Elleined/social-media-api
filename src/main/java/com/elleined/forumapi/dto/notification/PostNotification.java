package com.elleined.forumapi.dto.notification;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostNotification extends Notification {
    private final int postId;

    @Builder
    public PostNotification(int id, String message, int receiverId, String respondentPicture, int respondentId, String formattedDate, String formattedTime, String notificationStatus, int postId) {
        super(id, message, receiverId, respondentPicture, respondentId, formattedDate, formattedTime, notificationStatus);
        this.postId = postId;
    }
}
