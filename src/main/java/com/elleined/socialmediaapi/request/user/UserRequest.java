package com.elleined.socialmediaapi.request.user;

import com.elleined.socialmediaapi.request.Request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserRequest extends Request {

    @NotBlank(message = "Please provide your name")
    private String name;

    @NotBlank(message = "Please provide your picture")
    private String picture;

    @NotBlank(message = "Please provide your email")
    @Email(message = "Please provide valid email")
    private String email;

    @Builder
    public UserRequest(String name,
                       String picture,
                       String email) {
        this.name = name;
        this.picture = picture;
        this.email = email;
    }
}
