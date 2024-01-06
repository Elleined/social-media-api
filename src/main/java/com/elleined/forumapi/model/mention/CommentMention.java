package com.elleined.forumapi.model.mention;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "tbl_comment_mention")
@NoArgsConstructor
public final class CommentMention extends Mention {

    @ManyToOne
    @JoinColumn(
            name = "comment_id",
            referencedColumnName = "comment_id"
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
