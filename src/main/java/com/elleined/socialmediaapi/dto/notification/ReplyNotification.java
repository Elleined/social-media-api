package com.elleined.socialmediaapi.dto.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyNotification extends Notification {
    private int postId;
    private int commentId;
    private int replyId;
    private int count;

    @Builder
    public ReplyNotification(int id, String message, int receiverId, String respondentPicture, int respondentId, String formattedDate, String formattedTime, String notificationStatus, int postId, int commentId, int replyId, int count) {
        super(id, message, receiverId, respondentPicture, respondentId, formattedDate, formattedTime, notificationStatus);
        this.postId = postId;
        this.commentId = commentId;
        this.replyId = replyId;
        this.count = count;
    }
}
