package com.elleined.socialmediaapi.model.notification.mention;

import com.elleined.socialmediaapi.model.main.reply.Reply;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(region = "replyMentionNotificationCache", usage = CacheConcurrencyStrategy.READ_WRITE)

@Entity
@Table(name = "tbl_reply_mention_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ReplyMentionNotification extends MentionNotification {

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
        return STR."\{this.getCreator().getName()} mentioned you and others in a reply: \"\{this.getReply().getBody()}\" ";
    }
}
