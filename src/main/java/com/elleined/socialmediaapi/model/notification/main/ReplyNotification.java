package com.elleined.socialmediaapi.model.notification.main;

import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.notification.Notification;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_reply_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ReplyNotification extends Notification {

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
        return STR."\{this.getCreator().getName()} replied on your comment: \{this.getReply().getComment().getBody()}";
    }
}
