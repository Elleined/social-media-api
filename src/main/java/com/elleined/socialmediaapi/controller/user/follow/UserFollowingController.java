package com.elleined.socialmediaapi.controller.user.follow;

import com.elleined.socialmediaapi.dto.UserDTO;
import com.elleined.socialmediaapi.mapper.UserMapper;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.service.UserService;
import com.elleined.socialmediaapi.service.follow.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{currentUserId}/followings")
@RequiredArgsConstructor
public class UserFollowingController {
    private final UserService userService;
    private final UserMapper userMapper;

    private final FollowService followService;

    @GetMapping
    public List<UserDTO> getAllFollowing(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return followService.getAllFollowing(currentUser).stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @PostMapping("/{userToFollowId}")
    public void follow(@PathVariable("currentUserId") int currentUserId,
                       @PathVariable("userToFollowId") int userToFollowId) {
        User currentUser = userService.getById(currentUserId);
        User userToFollow = userService.getById(userToFollowId);

        followService.follow(currentUser, userToFollow);
    }

    @DeleteMapping("/{userToUnFollowId}")
    public void unFollow(@PathVariable("currentUserId") int currentUserId,
                         @PathVariable("userToUnFollowId") int userToUnFollowId) {
        User currentUser = userService.getById(currentUserId);
        User userToUnFollow = userService.getById(userToUnFollowId);

        followService.unFollow(currentUser, userToUnFollow);
    }
}
