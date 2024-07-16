package com.elleined.socialmediaapi.model.notification.reaction;

import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.reaction.Emoji;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(region = "replyReactionNotificationCache", usage = CacheConcurrencyStrategy.READ_WRITE)

@Entity
@Table(name = "tbl_reply_reaction_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ReplyReactionNotification extends ReactionNotification {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "reply_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Reply reply;

    @Override
    public String getMessage() {
        if (this.getReaction().getEmoji().getType().equals(Emoji.Type.HEART.name()))
            return STR."\{this.getCreator().getName()} loves to your reply: \"\{this.getReply().getBody()}\" ";

        return STR."\{this.getCreator().getName()} reacted to your reply: \"\{this.getReply().getBody()}\" ";
    }
}
