package com.elleined.socialmediaapi.dto.notification;

import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.model.notification.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO extends DTO {
    private String message;
    private Notification.Status status;
    private Set<Integer> postIds;
    private Set<Integer> commentIds;
    private Set<Integer> replyIds;
    private Set<Integer> reactIds;
    private Set<Integer> mentionIds;
    private Set<Integer> friendRequestIds;

    @Builder
    public NotificationDTO(int id,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt,
                           String message,
                           Notification.Status status,
                           Set<Integer> postIds,
                           Set<Integer> commentIds,
                           Set<Integer> replyIds,
                           Set<Integer> reactIds,
                           Set<Integer> mentionIds,
                           Set<Integer> friendRequestIds) {
        super(id, createdAt, updatedAt);
        this.message = message;
        this.status = status;
        this.postIds = postIds;
        this.commentIds = commentIds;
        this.replyIds = replyIds;
        this.reactIds = reactIds;
        this.mentionIds = mentionIds;
        this.friendRequestIds = friendRequestIds;
    }
}
