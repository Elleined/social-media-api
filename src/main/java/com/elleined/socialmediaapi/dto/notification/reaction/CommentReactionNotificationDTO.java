package com.elleined.socialmediaapi.dto.notification.reaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CommentReactionNotificationDTO extends ReactionNotificationDTO {
    private int commentId;
}