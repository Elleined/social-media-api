package com.elleined.socialmediaapi.dto.main;

import com.elleined.socialmediaapi.model.main.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PostDTO extends ForumDTO {
    private Post.CommentSectionStatus commentSectionStatus;
    private CommentDTO pinnedCommentDTO;

}
