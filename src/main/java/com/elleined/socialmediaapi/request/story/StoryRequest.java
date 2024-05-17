package com.elleined.socialmediaapi.request.story;

import com.elleined.socialmediaapi.request.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StoryRequest extends Request {
    @Positive(message = "Please provide your id")
    private int creatorId;

    @NotBlank(message = "Please provide your content")
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
