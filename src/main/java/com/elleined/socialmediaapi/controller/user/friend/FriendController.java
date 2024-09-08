package com.elleined.socialmediaapi.controller.user.friend;

import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.friend.FriendService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/friends")
public class FriendController {

    private final FriendService friendService;

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public Page<UserDTO> getAllFriends(@RequestHeader("Authorization") String jwt,
                                       @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                       @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                       @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                       @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return friendService.getAllFriends(currentUser, pageable)
                .map(userMapper::toDTO);
    }

    @DeleteMapping("/{userToUnFriendId}/unFriend")
    public void unFriend(@RequestHeader("Authorization") String jwt,
                         @PathVariable("userToUnFriendId") int userToUnFriendId) {
        User currentUser = userService.getByJWT(jwt);
        User userToUnFriend = userService.getById(userToUnFriendId);
        friendService.unFriend(currentUser, userToUnFriend);
    }
}
