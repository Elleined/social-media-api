package com.elleined.forumapi.dto.notification;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReplyNotification extends Notification {
    private final int postId;
    private final int commentId;
    private final int replyId;
    private final int count;

    @Builder
    public ReplyNotification(int id, String message, int receiverId, String respondentPicture, int respondentId, String formattedDate, String formattedTime, String notificationStatus, int postId, int commentId, int replyId, int count) {
        super(id, message, receiverId, respondentPicture, respondentId, formattedDate, formattedTime, notificationStatus);
        this.postId = postId;
        this.commentId = commentId;
        this.replyId = replyId;
        this.count = count;
    }
}
