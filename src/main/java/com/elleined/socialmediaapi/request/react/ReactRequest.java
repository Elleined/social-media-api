package com.elleined.socialmediaapi.request.react;

import com.elleined.socialmediaapi.request.Request;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ReactRequest extends Request {
    @Positive(message = "Please provide your id")
    private int creatorId;

    @Positive(message = "Please provide emoji id")
    private int emojiId;

    @Positive(message = "Please provide forum id")
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
