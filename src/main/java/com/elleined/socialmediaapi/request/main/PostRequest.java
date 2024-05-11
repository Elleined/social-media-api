package com.elleined.socialmediaapi.request.main;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class PostRequest extends ForumRequest {

    @Builder
    public PostRequest(String body,
                       int creatorId,
                       String attachedPicture,
                       Set<Integer> hashTagIds,
                       Set<Integer> mentionIds) {
        super(body, creatorId, attachedPicture, hashTagIds, mentionIds);
    }
}
