package com.elleined.socialmediaapi.dto.main;

import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.post.Post;
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
    private List<Integer> commentIds;
    private Set<Integer> savingUserIds;
    private Set<Integer> sharerIds;

    @Builder
    public PostDTO(int id,
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
                   Post.CommentSectionStatus commentSectionStatus,
                   int pinnedCommentId,
                   List<Integer> commentIds,
                   Set<Integer> savingUserIds,
                   Set<Integer> sharerIds) {
        super(id, createdAt, updatedAt, body, status, attachedPicture, creatorId, hashTagIds, mentionIds, reactionIds, notificationIds);
        this.commentSectionStatus = commentSectionStatus;
        this.pinnedCommentId = pinnedCommentId;
        this.commentIds = commentIds;
        this.savingUserIds = savingUserIds;
        this.sharerIds = sharerIds;
    }
}
