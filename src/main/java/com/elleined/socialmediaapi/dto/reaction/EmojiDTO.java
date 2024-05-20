package com.elleined.socialmediaapi.dto.reaction;

import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.model.react.Emoji;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder(builderMethodName = "emojiDtoBuilder")
@NoArgsConstructor
public class EmojiDTO extends DTO {
    private Emoji.Type type;
}
