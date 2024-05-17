package com.elleined.socialmediaapi.model.react;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "tbl_emoji")
@NoArgsConstructor
@Getter
@Setter
public class Emoji extends PrimaryKeyIdentity {

    @Enumerated(EnumType.STRING)
    @Column(
            name = "emoji_type",
            nullable = false
    )
    private Type type;

    @Builder
    public Emoji(int id,
                 LocalDateTime createdAt,
                 LocalDateTime updatedAt,
                 Type type) {
        super(id, createdAt, updatedAt);
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
