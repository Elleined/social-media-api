package com.elleined.forumapi.model.mention;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
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
@Table(name = "tbl_reply_mention")
@NoArgsConstructor
public class ReplyMention extends Mention {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "reply_id",
            referencedColumnName = "reply_id",
            nullable = false,
            updatable = false
    )
    private Reply reply;

    @Builder(builderMethodName = "replyMentionBuilder")

    public ReplyMention(int id, LocalDateTime createdAt, User mentionedUser, NotificationStatus notificationStatus, User mentioningUser, Reply reply) {
        super(id, createdAt, mentionedUser, notificationStatus, mentioningUser);
        this.reply = reply;
    }

    @Override
    public String getMessage() {
        return this.getMentioningUser().getName() + " mentioned you in a reply: " + "\"" + this.getReply().getBody() + "\"";
    }

    @Override
    public int getReceiverId() {
        return getMentionedUser().getId();
    }

    @Override
    public boolean isEntityActive() {
        return this.getReply().isActive();
    }
}
