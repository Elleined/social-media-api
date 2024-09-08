package com.elleined.socialmediaapi.controller.user;

import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @PostMapping
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password) {

        return userService.login(email, password);
    }
}
