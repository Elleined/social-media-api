package com.elleined.forumapi.model.emoji;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_emoji")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Emoji {

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

    public Emoji(Type type) {
        this.type = type;
    }

    public enum Type {
        HEART,
        CARE,
        HAHA,
        WOW,
        SAD,
        ANGRY
    }
}
