package com.elleined.socialmediaapi.dto.reaction;

import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.model.react.Emoji;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder(builderMethodName = "emojiDtoBuilder")
public class EmojiDTO extends DTO {
    private Emoji.Type type;
}
