package com.forum.application.dto;

import lombok.Builder;

@Builder
public record UserDTO(int id,
                      String picture,
                      String name) {
}
