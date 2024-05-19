package com.elleined.socialmediaapi.dto.reaction;

import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.model.react.Emoji;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class EmojiDTO extends DTO {
    private Emoji.Type type;

    @Builder
    public EmojiDTO(int id,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt,
                    Emoji.Type type) {
        super(id, createdAt, updatedAt);
        this.type = type;
    }
}
