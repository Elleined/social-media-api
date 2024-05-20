package com.elleined.socialmediaapi.dto.main;

import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.model.main.vote.Vote;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Getter
@Setter
@SuperBuilder(builderMethodName = "voteDtoBuilder")
@NoArgsConstructor
public class VoteDTO extends DTO {
    private int creatorId;
    private int commentId;
    private Vote.Verdict verdict;
}
