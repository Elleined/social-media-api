package com.elleined.forumapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class NoteDTO {
    private int id;
    private int userId;
    private String thought;
    private LocalDateTime createdAt;
}
