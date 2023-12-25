package com.elleined.forumapi.dto;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.emoji.Emoji;
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
