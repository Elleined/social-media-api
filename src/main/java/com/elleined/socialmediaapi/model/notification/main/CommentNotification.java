package com.elleined.socialmediaapi.model.notification.main;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.notification.Notification;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(region = "commentNotificationCache", usage = CacheConcurrencyStrategy.READ_WRITE)

@Entity
@Table(name = "tbl_comment_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CommentNotification extends Notification {

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
        return STR."\{this.getCreator().getName()} commented on your post: \"\{this.getComment().getPost().getBody()}\" ";
    }
}
