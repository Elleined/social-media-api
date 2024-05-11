package com.elleined.socialmediaapi.newdto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class DTO {
    private int id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}