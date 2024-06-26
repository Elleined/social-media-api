package com.elleined.socialmediaapi.dto.main;

import com.elleined.socialmediaapi.model.main.post.Post;
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
public class PostDTO extends ForumDTO {
    private Post.CommentSectionStatus commentSectionStatus;
    private int pinnedCommentId;
    private List<Integer> commentIds;
    private Set<Integer> savingUserIds;
    private Set<Integer> sharerIds;

}
