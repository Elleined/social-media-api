package com.elleined.socialmediaapi.model.vote;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "tbl_comment_vote")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Vote extends PrimaryKeyIdentity {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User creator;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "comment_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Comment comment;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "verdict",
            nullable = false
    )
    private Verdict verdict;

    public enum Verdict {
        UP_VOTE,
        DOWN_VOTE
    }
}
