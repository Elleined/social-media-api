package com.elleined.forumapi.model.like;

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

@Entity
@Table(name = "tbl_comment_like")
@NoArgsConstructor
public final class CommentLike extends Like {

    @ManyToOne
    @JoinColumn(
            name = "comment_id",
            referencedColumnName = "comment_id"
    )
    @Getter
    private Comment comment;

    @Builder(builderMethodName = "commentLikeBuilder")
    public CommentLike(int id, User respondent, LocalDateTime createdAt, NotificationStatus notificationStatus, Comment comment) {
        super(id, respondent, createdAt, notificationStatus);
        this.comment = comment;
    }

    @Override
    public String getMessage() {
        return this.getRespondent().getName() + " liked your comment: " +  "\"" + this.getComment().getBody() + "\"";
    }

    @Override
    public int getReceiverId() {
        return comment.getCommenter().getId();
    }

}
