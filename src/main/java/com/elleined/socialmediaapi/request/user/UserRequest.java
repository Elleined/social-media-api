package com.elleined.socialmediaapi.request.user;

import com.elleined.socialmediaapi.request.Request;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserRequest extends Request {
    private String name;
    private String picture;
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
