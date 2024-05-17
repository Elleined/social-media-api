package com.elleined.socialmediaapi.dto.main;

import com.elleined.socialmediaapi.model.NotificationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class ReplyDTO extends ForumDTO {
    private int commentId;
    private Set<Integer> hashTagIds;
    private Set<Integer> mentionIds;
    private Set<Integer> reactionIds;

    @Builder
    public ReplyDTO(int id,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt,
                    String body,
                    Status status,
                    String attachedPicture,
                    int creatorId,
                    NotificationStatus notificationStatus,
                    int commentId,
                    Set<Integer> hashTagIds,
                    Set<Integer> mentionIds,
                    Set<Integer> reactionIds) {
        super(id, createdAt, updatedAt, body, status, attachedPicture, creatorId, notificationStatus);
        this.commentId = commentId;
        this.hashTagIds = hashTagIds;
        this.mentionIds = mentionIds;
        this.reactionIds = reactionIds;
    }
}
