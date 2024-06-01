package com.elleined.socialmediaapi.model.reaction;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "tbl_emoji")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Emoji extends PrimaryKeyIdentity {

    @Column(
            name = "emoji_type",
            nullable = false
    )
    private String type;

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
