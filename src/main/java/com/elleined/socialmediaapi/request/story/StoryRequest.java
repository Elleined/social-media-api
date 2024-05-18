package com.elleined.socialmediaapi.request.story;

import com.elleined.socialmediaapi.request.Request;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StoryRequest extends Request {
    @NotBlank(message = "Please provide your content")
    private String content;

    private String attachPicture;

    @Builder
    public StoryRequest(String content, String attachPicture) {
        this.content = content;
        this.attachPicture = attachPicture;
    }
}
