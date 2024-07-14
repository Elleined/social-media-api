package com.elleined.socialmediaapi.dto.vote;

import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.model.vote.Vote;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class VoteDTO extends DTO {
    private UserDTO creatorDTO;
    private CommentDTO commentDTO;
    private Vote.Verdict verdict;
}
