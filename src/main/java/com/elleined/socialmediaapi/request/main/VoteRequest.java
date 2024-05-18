package com.elleined.socialmediaapi.request.main;

import com.elleined.socialmediaapi.model.main.vote.Vote;
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
public class VoteRequest extends Request {
    @Positive(message = "Please provide your id")
    private int creatorId;

    @NotBlank(message = "Please provide the post id")
    private int postId;

    @NotBlank(message = "Please provide the comment id")
    private int commentId;

    @NotBlank(message = "Please provide your verdict (UP_VOTE or DOWN_VOTE)")
    private Vote.Verdict verdict;

    @Builder
    public VoteRequest(int creatorId,
                       int postId,
                       int commentId,
                       Vote.Verdict verdict) {
        this.creatorId = creatorId;
        this.postId = postId;
        this.commentId = commentId;
        this.verdict = verdict;
    }
}
