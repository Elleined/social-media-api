package com.elleined.socialmediaapi.dto.story;

import com.elleined.socialmediaapi.dto.DTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class StoryDTO extends DTO {
    private String content;
    private String attachPicture;
    private int creatorId;

    @Builder
    public StoryDTO(int id, LocalDateTime createdAt, LocalDateTime updatedAt, String content, String attachPicture, int creatorId) {
        super(id, createdAt, updatedAt);
        this.content = content;
        this.attachPicture = attachPicture;
        this.creatorId = creatorId;
    }
}
