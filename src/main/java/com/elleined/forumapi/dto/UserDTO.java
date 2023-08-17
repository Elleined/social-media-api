package com.elleined.forumapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserDTO(int id,
                      @NotBlank(message = "Picture  cannot be null, empty, or null")
                      String picture,
                       @NotBlank(message = "Name  cannot be null, empty, or null")
                      String name,
                       @NotBlank(message = "Email  cannot be null, empty, or null")
                      String email,
                       @NotBlank(message = "UUID  cannot be null, empty, or null")
                      String UUID) {
}
