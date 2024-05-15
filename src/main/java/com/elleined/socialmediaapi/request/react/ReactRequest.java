package com.elleined.socialmediaapi.request.react;

import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.request.Request;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ReactRequest extends Request {
    private int creatorId;
    private int emojiId;
    private int forumId;

    @Builder
    public ReactRequest(int creatorId,
                        int emojiId,
                        int forumId) {
        this.creatorId = creatorId;
        this.emojiId = emojiId;
        this.forumId = forumId;
    }
}
