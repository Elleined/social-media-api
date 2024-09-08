package com.elleined.socialmediaapi.controller.user;

import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDTO save(@RequestParam("name") String name,
                        @RequestParam("picture") String picture,
                        @RequestParam("email") String email,
                        @RequestParam("password") String password) {

        User user = userService.register(name, email, password, picture);
        return userMapper.toDTO(user);
    }
}
