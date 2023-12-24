package com.elleined.forumapi.model.emoji.react;

import com.elleined.forumapi.model.Comment;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

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

    @Override
    public String getMessage() {
        return this.getRespondent().getName() + " reacted " + this.getEmoji().getType().name() + " to you're comment " + comment.getBody();
    }

    @Override
    public int getReceiverId() {
        return comment.getCommenter().getId();
    }
}
