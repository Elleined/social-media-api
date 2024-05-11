package com.elleined.socialmediaapi.newdto.main;

import com.elleined.socialmediaapi.model.NotificationStatus;
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
public class CommentDTO extends ForumDTO {
    private int postId;
    private int pinnedReplyId;
    private Set<Integer> hashTagIds;
    private Set<Integer> mentionIds;
    private Set<Integer> reactionIds;
    private List<Integer> replyIds;
    private Set<Integer> userVoteIds;

    @Builder
    public CommentDTO(int id,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt,
                      String body,
                      Status status,
                      String attachedPicture,
                      int creatorId,
                      NotificationStatus notificationStatus,
                      int postId,
                      int pinnedReplyId,
                      Set<Integer> hashTagIds,
                      Set<Integer> mentionIds,
                      Set<Integer> reactionIds,
                      List<Integer> replyIds,
                      Set<Integer> userVoteIds) {
        super(id, createdAt, updatedAt, body, status, attachedPicture, creatorId, notificationStatus);
        this.postId = postId;
        this.pinnedReplyId = pinnedReplyId;
        this.hashTagIds = hashTagIds;
        this.mentionIds = mentionIds;
        this.reactionIds = reactionIds;
        this.replyIds = replyIds;
        this.userVoteIds = userVoteIds;
    }
}
