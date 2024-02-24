package com.elleined.socialmediaapi.model.react;

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
@Table(name = "tbl_comment_emoji")
@NoArgsConstructor
public class CommentReact extends React {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "comment_id",
            referencedColumnName = "comment_id",
            nullable = false,
            updatable = false
    )
    private Comment comment;

    @Builder(builderMethodName = "commentReactBuilder")
    public CommentReact(int id, LocalDateTime createdAt, User respondent, NotificationStatus notificationStatus, Emoji emoji, Comment comment) {
        super(id, createdAt, respondent, notificationStatus, emoji);
        this.comment = comment;
    }

    @Override
    public String getMessage() {
        return this.getRespondent().getName() + " reacted " + this.getEmoji().getType().name() + " to you're comment " + comment.getBody();
    }

    @Override
    public int getReceiverId() {
        return comment.getCommenter().getId();
    }
}
