package com.elleined.forumapi.model.emoji;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_emoji")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Emoji {
    // createdAt
    // notificationStatus
    // respondent == user
    // saan may emoji sa post, comment, or reply ba
    // !!!! This is not yet finish

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            nullable = false,
            updatable = false,
            unique = true
    )
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "emoji_type",
            nullable = false
    )
    private Type type;

    @ManyToMany
    @JoinTable(
            name = "tbl_comment_emoji",
            joinColumns = @JoinColumn(
                    name = "emoji_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "comment_id",
                    referencedColumnName = "comment_id"
            )
    )
    private List<Comment> commentEmojis;

    public enum Type {
        LIKE,
        HEART,
        CARE,
        HAHA,
        WOW,
        SAD,
        ANGRY
    }
}
