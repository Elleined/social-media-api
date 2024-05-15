package com.elleined.socialmediaapi.dto.note;

import com.elleined.socialmediaapi.dto.DTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class NoteDTO extends DTO {
    private String thought;
    private int creatorId;

    @Builder
    public NoteDTO(int id,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   String thought,
                   int creatorId) {
        super(id, createdAt, updatedAt);
        this.thought = thought;
        this.creatorId = creatorId;
    }
}
