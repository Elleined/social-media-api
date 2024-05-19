package com.elleined.socialmediaapi.dto.main;

import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.model.main.Forum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public abstract class ForumDTO extends DTO {
    private String body;
    private Forum.Status status;
    private String attachedPicture;
    private int creatorId;
    private Set<Integer> mentionIds;
    private Set<Integer> reactionIds;
    private Set<Integer> notificationIds;

    public ForumDTO(int id,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt,
                    String body,
                    Forum.Status status,
                    String attachedPicture,
                    int creatorId,
                    Set<Integer> mentionIds,
                    Set<Integer> reactionIds,
                    Set<Integer> notificationIds) {
        super(id, createdAt, updatedAt);
        this.body = body;
        this.status = status;
        this.attachedPicture = attachedPicture;
        this.creatorId = creatorId;
        this.mentionIds = mentionIds;
        this.reactionIds = reactionIds;
        this.notificationIds = notificationIds;
    }
}
