package com.elleined.socialmediaapi.request.main;

import com.elleined.socialmediaapi.request.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public abstract class ForumRequest extends Request {
    @NotBlank(message = "Please provide your content")
    private String body;

    @Positive(message = "Please provide creator/ current user id")
    private int creatorId;

    private String attachedPicture;

    private Set<Integer> hashTagIds;

    private Set<Integer> mentionedUserIds;

    public ForumRequest(String body,
                        int creatorId,
                        String attachedPicture,
                        Set<Integer> hashTagIds,
                        Set<Integer> mentionedUserIds) {
        this.body = body;
        this.creatorId = creatorId;
        this.attachedPicture = attachedPicture;
        this.hashTagIds = hashTagIds;
        this.mentionedUserIds = mentionedUserIds;
    }
}
