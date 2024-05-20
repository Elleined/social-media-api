package com.elleined.socialmediaapi.dto;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class DTO {
    private int id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}