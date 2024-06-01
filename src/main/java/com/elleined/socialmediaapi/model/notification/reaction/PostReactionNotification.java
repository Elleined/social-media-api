package com.elleined.socialmediaapi.model.notification.reaction;

import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.reaction.Emoji;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_post_reaction_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PostReactionNotification extends ReactionNotification {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Post post;

    @Override
    public String getMessage() {
        if (this.getReaction().getEmoji().getType().equals(Emoji.Type.HEART.name()))
            return STR."\{this.getCreator().getName()} loves to your post: \"\{this.getPost().getBody()}\" ";

        return STR."\{this.getCreator().getName()} reacted to your post: \"\{this.getPost().getBody()}\" ";
    }
}
