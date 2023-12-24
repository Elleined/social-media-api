package com.elleined.forumapi.model.emoji;

import com.elleined.forumapi.model.Reply;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_comment_emoji")
@NoArgsConstructor
public class ReplyEmoji extends EntityEmoji {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "reply_id",
            referencedColumnName = "reply_id",
            nullable = false,
            updatable = false
    )
    private Reply reply;

    @Override
    public String getMessage() {
        return this.getRespondent().getName() + " reacted " + this.getEmoji().getType().name() + " to you're reply " + reply.getBody();
    }

    @Override
    public int getReceiverId() {
        return this.reply.getReplier().getId();
    }
}
