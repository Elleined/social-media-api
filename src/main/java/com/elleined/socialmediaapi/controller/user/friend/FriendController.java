package com.elleined.socialmediaapi.controller.user.friend;

import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.friend.FriendService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/friends")
public class FriendController {

    private final FriendService friendService;

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public Set<UserDTO> getAllFriends(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return friendService.getAllFriends(currentUser).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @DeleteMapping("/{userToUnFriendId}")
    public void unFriend(@PathVariable("currentUserId") int currentUserId,
                         @PathVariable("userToUnFriendId") int userToUnFriendId) {
        User currentUser = userService.getById(currentUserId);
        User userToUnFriend = userService.getById(userToUnFriendId);
        friendService.unFriend(currentUser, userToUnFriend);
    }
}
