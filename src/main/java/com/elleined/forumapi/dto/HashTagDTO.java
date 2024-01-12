package com.elleined.forumapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class HashTagDTO {
    private int id;
    private String keyword;
    private LocalDateTime createdAt;
}
