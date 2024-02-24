package com.elleined.socialmediaapi.dto.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostNotification extends Notification {
    private int postId;

    @Builder
    public PostNotification(int id, String message, int receiverId, String respondentPicture, int respondentId, String formattedDate, String formattedTime, String notificationStatus, int postId) {
        super(id, message, receiverId, respondentPicture, respondentId, formattedDate, formattedTime, notificationStatus);
        this.postId = postId;
    }
}
