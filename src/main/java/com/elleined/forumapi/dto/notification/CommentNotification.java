package com.elleined.forumapi.dto.notification;


import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentNotification extends Notification {

    private final int postId;
    private final int commentId;
    private final int count;

    @Builder
    public CommentNotification(int id, String message, int receiverId, String respondentPicture, int respondentId, String formattedDate, String formattedTime, String notificationStatus, int postId, int commentId, int count) {
        super(id, message, receiverId, respondentPicture, respondentId, formattedDate, formattedTime, notificationStatus);
        this.postId = postId;
        this.commentId = commentId;
        this.count = count;
    }
}
