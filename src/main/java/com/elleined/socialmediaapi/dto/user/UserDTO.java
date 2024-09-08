package com.elleined.socialmediaapi.dto.user;

import com.elleined.socialmediaapi.dto.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserDTO extends DTO {
    private String name;
    private String email;
    private String picture;
    private String UUID;
}
