package com.elleined.socialmediaapi.newdto.main;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.main.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO extends ForumDTO {
    private Post.CommentSectionStatus commentSectionStatus;
    private int pinnedCommentId;
    private Set<Integer> hashTagIds;
    private Set<Integer> mentionIds;
    private Set<Integer> reactionIds;
    private List<Integer> commentIds;
    private Set<Integer> savingUserIds;
    private Set<Integer> sharerIds;

    @Builder
    public PostDTO(int id,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   String body,
                   Status status,
                   String attachedPicture,
                   int creatorId,
                   NotificationStatus notificationStatus,
                   Post.CommentSectionStatus commentSectionStatus,
                   int pinnedCommentId,
                   Set<Integer> hashTagIds,
                   Set<Integer> mentionIds,
                   Set<Integer> reactionIds,
                   List<Integer> commentIds,
                   Set<Integer> savingUserIds,
                   Set<Integer> sharerIds) {
        super(id, createdAt, updatedAt, body, status, attachedPicture, creatorId, notificationStatus);
        this.commentSectionStatus = commentSectionStatus;
        this.pinnedCommentId = pinnedCommentId;
        this.hashTagIds = hashTagIds;
        this.mentionIds = mentionIds;
        this.reactionIds = reactionIds;
        this.commentIds = commentIds;
        this.savingUserIds = savingUserIds;
        this.sharerIds = sharerIds;
    }
}
