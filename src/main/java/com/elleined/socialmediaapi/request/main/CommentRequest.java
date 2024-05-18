package com.elleined.socialmediaapi.request.main;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class CommentRequest extends ForumRequest {
    @Positive(message = "Please provide post id")
    private int postId;

    @Builder
    public CommentRequest(String body,
                          int creatorId,
                          String attachedPicture,
                          Set<Integer> hashTagIds,
                          Set<Integer> mentionedUserIds,
                          int postId) {
        super(body, creatorId, attachedPicture, hashTagIds, mentionedUserIds);
        this.postId = postId;
    }
}