package com.elleined.socialmediaapi.model.notification.mention;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(region = "commentMentionNotificationCache", usage = CacheConcurrencyStrategy.READ_WRITE)

@Entity
@Table(name = "tbl_comment_mention_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CommentMentionNotification extends MentionNotification {

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
        return STR."\{this.getCreator().getName()} mentioned you and others in a comment: \"\{this.getComment().getBody()}\" ";
    }
}
