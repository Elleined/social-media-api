package com.elleined.socialmediaapi.dto.main;

import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@SuperBuilder(builderMethodName = "postDtoBuilder")
@NoArgsConstructor
public class PostDTO extends ForumDTO {
    private Post.CommentSectionStatus commentSectionStatus;
    private int pinnedCommentId;
    private List<Integer> commentIds;
    private Set<Integer> savingUserIds;
    private Set<Integer> sharerIds;

}
