package com.elleined.socialmediaapi.controller.user.follow;

import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.follow.FollowService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/{currentUserId}/followers")
@RequiredArgsConstructor
public class UserFollowerController {
    private final UserService userService;
    private final UserMapper userMapper;

    private final FollowService followService;

    @GetMapping
    public List<UserDTO> getAllFollowers(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return followService.getAllFollowers(currentUser).stream()
                .map(userMapper::toDTO)
                .toList();
    }
}
