package com.elleined.forumapi.dto.notification;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendRequestNotification extends Notification {

    @Builder
    public FriendRequestNotification(int id, String message, int receiverId, String respondentPicture, int respondentId, String formattedDate, String formattedTime, String notificationStatus) {
        super(id, message, receiverId, respondentPicture, respondentId, formattedDate, formattedTime, notificationStatus);
    }
}
