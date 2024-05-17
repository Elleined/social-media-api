package com.elleined.socialmediaapi.dto.main;

import com.elleined.socialmediaapi.model.main.Forum;
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

    @Builder
    public ReplyDTO(int id,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt,
                    String body,
                    Forum.Status status,
                    String attachedPicture,
                    int creatorId,
                    Set<Integer> hashTagIds,
                    Set<Integer> mentionIds,
                    Set<Integer> reactionIds,
                    Set<Integer> notificationIds,
                    int commentId) {
        super(id, createdAt, updatedAt, body, status, attachedPicture, creatorId, hashTagIds, mentionIds, reactionIds, notificationIds);
        this.commentId = commentId;
    }
}
