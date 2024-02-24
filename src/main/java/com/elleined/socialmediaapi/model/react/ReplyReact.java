package com.elleined.socialmediaapi.model.react;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.Reply;
import com.elleined.socialmediaapi.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tbl_reply_emoji")
@NoArgsConstructor
public class ReplyReact extends React {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "reply_id",
            referencedColumnName = "reply_id",
            nullable = false,
            updatable = false
    )
    private Reply reply;

    @Builder(builderMethodName = "replyReactBuilder")
    public ReplyReact(int id, LocalDateTime createdAt, User respondent, NotificationStatus notificationStatus, Emoji emoji, Reply reply) {
        super(id, createdAt, respondent, notificationStatus, emoji);
        this.reply = reply;
    }

    @Override
    public String getMessage() {
        return this.getRespondent().getName() + " reacted " + this.getEmoji().getType().name() + " to you're reply " + reply.getBody();
    }

    @Override
    public int getReceiverId() {
        return this.reply.getReplier().getId();
    }
}
