package com.elleined.socialmediaapi.dto.main;

import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.model.main.vote.Vote;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
public class VoteDTO extends DTO {
    private int creatorId;
    private int commentId;
    private Vote.Verdict verdict;

    @Builder
    public VoteDTO(int id,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   int creatorId,
                   int commentId,
                   Vote.Verdict verdict) {
        super(id, createdAt, updatedAt);
        this.creatorId = creatorId;
        this.commentId = commentId;
        this.verdict = verdict;
    }
}
