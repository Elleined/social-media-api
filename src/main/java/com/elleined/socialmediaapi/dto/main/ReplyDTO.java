package com.elleined.socialmediaapi.dto.main;

import com.elleined.socialmediaapi.model.main.Forum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@SuperBuilder(builderMethodName = "replyDtoBuilder")
@NoArgsConstructor
public class ReplyDTO extends ForumDTO {
    private int commentId;
}
