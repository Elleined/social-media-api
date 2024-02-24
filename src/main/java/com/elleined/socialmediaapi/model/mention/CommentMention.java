package com.elleined.socialmediaapi.model.mention;

import com.elleined.socialmediaapi.model.Comment;
import com.elleined.socialmediaapi.model.NotificationStatus;
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
@Table(name = "tbl_comment_mention")
@NoArgsConstructor
public class CommentMention extends Mention {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "comment_id",
            referencedColumnName = "comment_id",
            nullable = false,
            updatable = false
    )
    private Comment comment;

    @Builder(builderMethodName = "commentMentionBuilder")
    public CommentMention(int id, LocalDateTime createdAt, User mentionedUser, NotificationStatus notificationStatus, User mentioningUser, Comment comment) {
        super(id, createdAt, mentionedUser, notificationStatus, mentioningUser);
        this.comment = comment;
    }

    @Override
    public String getMessage() {
        return this.getMentioningUser().getName() + " mentioned you in a comment: " + "\"" + this.getComment().getBody() + "\"";
    }

    @Override
    public int getReceiverId() {
        return getMentionedUser().getId();
    }

    @Override
    public boolean isEntityActive() {
        return this.getComment().isActive();
    }
}
