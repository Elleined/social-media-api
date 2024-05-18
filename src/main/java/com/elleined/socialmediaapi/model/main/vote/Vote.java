package com.elleined.socialmediaapi.model.main.vote;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "tbl_comment_vote")
@NoArgsConstructor
@Getter
@Setter
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

    @Builder
    public Vote(int id,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                User creator,
                Comment comment,
                Verdict verdict) {
        super(id, createdAt, updatedAt);
        this.creator = creator;
        this.comment = comment;
        this.verdict = verdict;
    }

    public enum Verdict {
        UP_VOTE,
        DOWN_VOTE
    }
}
