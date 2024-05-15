package com.elleined.socialmediaapi.request.main;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class ReplyRequest extends ForumRequest {
    private int postId;
    private int commentId;

    @Builder
    public ReplyRequest(String body,
                        int creatorId,
                        String attachedPicture,
                        Set<Integer> hashTagIds,
                        Set<Integer> mentionedUserIds,
                        int postId,
                        int commentId) {
        super(body, creatorId, attachedPicture, hashTagIds, mentionedUserIds);
        this.postId = postId;
        this.commentId = commentId;
    }
}
