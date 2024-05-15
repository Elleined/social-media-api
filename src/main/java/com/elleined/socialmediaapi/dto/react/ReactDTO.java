package com.elleined.socialmediaapi.dto.react;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.react.Emoji;
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
public class ReactDTO extends DTO {
    private int creatorId;
    private NotificationStatus notificationStatus;
    private int emojiId;
    private Set<Integer> postIds;
    private Set<Integer> commentIds;
    private Set<Integer> replyIds;

    @Builder
    public ReactDTO(int id,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt,
                    int creatorId,
                    NotificationStatus notificationStatus,
                    int emojiId,
                    Set<Integer> postIds,
                    Set<Integer> commentIds,
                    Set<Integer> replyIds) {
        super(id, createdAt, updatedAt);
        this.creatorId = creatorId;
        this.notificationStatus = notificationStatus;
        this.emojiId = emojiId;
        this.postIds = postIds;
        this.commentIds = commentIds;
        this.replyIds = replyIds;
    }
}
