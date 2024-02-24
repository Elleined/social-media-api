package com.elleined.socialmediaapi.dto;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.react.Emoji;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReactionDTO {
    private int id;
    private LocalDateTime createdAt;
    private int respondentId;
    private NotificationStatus notificationStatus;
    private Emoji.Type emojiType;
    private int entityId; // Post, comment, or reply
}
