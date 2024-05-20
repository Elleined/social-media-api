package com.elleined.socialmediaapi.model.react;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Entity
@Table(name = "tbl_emoji")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Emoji extends PrimaryKeyIdentity {

    @Enumerated(EnumType.STRING)
    @Column(
            name = "emoji_type",
            nullable = false
    )
    private Type type;

    public Emoji(LocalDateTime createdAt, LocalDateTime updatedAt, Type type) {
        super(createdAt, updatedAt);
        this.type = type;
    }

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
