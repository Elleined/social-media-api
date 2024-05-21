package com.elleined.socialmediaapi.dto.reaction;

import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.model.react.Emoji;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class EmojiDTO extends DTO {
    private String type;
}
