package com.elleined.socialmediaapi.request.main;

import com.elleined.socialmediaapi.request.Request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public abstract class ForumRequest extends Request {
    private String body;
    private int creatorId;
    private String attachedPicture;
    private Set<Integer> hashTagIds;
    private Set<Integer> mentionIds;

    public ForumRequest(String body, int creatorId, String attachedPicture, Set<Integer> hashTagIds, Set<Integer> mentionIds) {
        this.body = body;
        this.creatorId = creatorId;
        this.attachedPicture = attachedPicture;
        this.hashTagIds = hashTagIds;
        this.mentionIds = mentionIds;
    }
}
