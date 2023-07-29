package com.forum.application.model.mention;

import com.forum.application.model.Comment;
import com.forum.application.model.NotificationStatus;
import com.forum.application.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_mention_comment")
@NoArgsConstructor
public final class CommentMention extends Mention {

    @ManyToOne
    @JoinColumn(
            name = "comment_id",
            referencedColumnName = "comment_id"
    )
    @Getter
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
}
