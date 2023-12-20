package com.elleined.forumapi.controller.user.friend;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.friend.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/friends")
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;

    @GetMapping
    public Set<User> getAllFriends(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return friendService.getAllFriends(currentUser);
    }

    @DeleteMapping("/{userToUnFriendId}")
    public void unFriend(@PathVariable("currentUserId") int currentUserId,
                         @PathVariable("userToUnFriendId") int userToUnFriendId) {
        User currentUser = userService.getById(currentUserId);
        User userToUnFriend = userService.getById(userToUnFriendId);
        friendService.unFriend(currentUser, userToUnFriend);
    }
}
