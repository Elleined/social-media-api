package com.elleined.socialmediaapi.dto.mention;

import com.elleined.socialmediaapi.model.NotificationStatus;
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
    private NotificationStatus notificationStatus;
    private Set<Integer> postIds;
    private Set<Integer> commentIds;
    private Set<Integer> replyIds;

    @Builder
    public MentionDTO(int id,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt,
                      int creatorId,
                      int mentionedUserId,
                      NotificationStatus notificationStatus,
                      Set<Integer> postIds,
                      Set<Integer> commentIds,
                      Set<Integer> replyIds) {
        super(id, createdAt, updatedAt);
        this.creatorId = creatorId;
        this.mentionedUserId = mentionedUserId;
        this.notificationStatus = notificationStatus;
        this.postIds = postIds;
        this.commentIds = commentIds;
        this.replyIds = replyIds;
    }
}
