package com.elleined.socialmediaapi.dto.main;

import com.elleined.socialmediaapi.model.main.Forum;
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
public class CommentDTO extends ForumDTO {
    private int postId;
    private int pinnedReplyId;
    private List<Integer> replyIds;
    private Set<Integer> userVoteIds;

    @Builder
    public CommentDTO(int id,
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
                      int postId,
                      int pinnedReplyId,
                      List<Integer> replyIds,
                      Set<Integer> userVoteIds) {
        super(id, createdAt, updatedAt, body, status, attachedPicture, creatorId, hashTagIds, mentionIds, reactionIds, notificationIds);
        this.postId = postId;
        this.pinnedReplyId = pinnedReplyId;
        this.replyIds = replyIds;
        this.userVoteIds = userVoteIds;
    }
}
