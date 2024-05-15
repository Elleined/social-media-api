package com.elleined.socialmediaapi.request.story;

import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.request.Request;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StoryRequest extends Request {
    private int creatorId;
    private String content;
    private String attachPicture;

    @Builder
    public StoryRequest(int creatorId,
                        String content,
                        String attachPicture) {
        this.creatorId = creatorId;
        this.content = content;
        this.attachPicture = attachPicture;
    }
}
