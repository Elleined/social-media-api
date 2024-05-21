package com.elleined.socialmediaapi.dto.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CommentDTO extends ForumDTO {
    private int postId;
    private int pinnedReplyId;
    private List<Integer> replyIds;
    private Set<Integer> voteIds;

}
