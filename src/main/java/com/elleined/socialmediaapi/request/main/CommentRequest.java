package com.elleined.socialmediaapi.request.main;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class CommentRequest extends ForumRequest {
    private int postId;

    @Builder
    public CommentRequest(String body,
                          int creatorId,
                          String attachedPicture,
                          Set<Integer> hashTagIds,
                          Set<Integer> mentionIds,
                          int postId) {
        super(body, creatorId, attachedPicture, hashTagIds, mentionIds);
        this.postId = postId;
    }
}
