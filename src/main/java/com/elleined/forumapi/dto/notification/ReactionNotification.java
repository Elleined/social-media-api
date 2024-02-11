package com.elleined.forumapi.dto.notification;

import com.elleined.forumapi.model.react.Emoji;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReactionNotification extends Notification {

    private Emoji.Type emojiType;

    @Builder
    public ReactionNotification(int id, String message, int receiverId, String respondentPicture, int respondentId, String formattedDate, String formattedTime, String notificationStatus, Emoji.Type emojiType) {
        super(id, message, receiverId, respondentPicture, respondentId, formattedDate, formattedTime, notificationStatus);
        this.emojiType = emojiType;
    }
}
