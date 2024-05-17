package com.elleined.socialmediaapi.dto.mention;

import com.elleined.socialmediaapi.dto.DTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class MentionDTO extends DTO {
    private int creatorId;
    private int mentionedUserId;
    private Set<Integer> notificationIds;
    private Set<Integer> postIds;
    private Set<Integer> commentIds;
    private Set<Integer> replyIds;

    @Builder
    public MentionDTO(int id,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt,
                      int creatorId,
                      int mentionedUserId,
                      Set<Integer> notificationIds,
                      Set<Integer> postIds,
                      Set<Integer> commentIds,
                      Set<Integer> replyIds) {
        super(id, createdAt, updatedAt);
        this.creatorId = creatorId;
        this.mentionedUserId = mentionedUserId;
        this.notificationIds = notificationIds;
        this.postIds = postIds;
        this.commentIds = commentIds;
        this.replyIds = replyIds;
    }
}
