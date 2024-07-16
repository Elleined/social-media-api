package com.elleined.socialmediaapi.model.notification.reaction;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.reaction.Emoji;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(region = "commentReactionNotificationCache", usage = CacheConcurrencyStrategy.READ_WRITE)

@Entity
@Table(name = "tbl_comment_reaction_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CommentReactionNotification extends ReactionNotification {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "comment_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Comment comment;

    @Override
    public String getMessage() {
        if (this.getReaction().getEmoji().getType().equals(Emoji.Type.HEART.name()))
            return STR."\{this.getCreator().getName()} loves to your comment: \"\{this.getComment().getBody()}\" ";

        return STR."\{this.getCreator().getName()} reacted to your comment: \"\{this.getComment().getBody()}\" ";
    }
}
