package com.elleined.forumapi.controller.user.friend;

import com.elleined.forumapi.dto.UserDTO;
import com.elleined.forumapi.mapper.UserMapper;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.friend.FriendService;
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
