package com.elleined.socialmediaapi.request.mention;

import com.elleined.socialmediaapi.request.Request;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MentionRequest extends Request {
    private int creatorId;
    private int mentionedUserId;
    private int forumId;

    @Builder
    public MentionRequest(int creatorId,
                          int mentionedUserId,
                          int forumId) {
        this.creatorId = creatorId;
        this.mentionedUserId = mentionedUserId;
        this.forumId = forumId;
    }
}
