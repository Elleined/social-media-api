package com.elleined.socialmediaapi.dto.main;

import com.elleined.socialmediaapi.model.main.Forum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
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
    private Set<Integer> userVoteIds;

}
