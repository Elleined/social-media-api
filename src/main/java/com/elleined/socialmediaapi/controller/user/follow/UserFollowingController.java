package com.elleined.socialmediaapi.controller.user.follow;

import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.follow.FollowService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<UserDTO> getAllFollowing(@PathVariable("currentUserId") int currentUserId,
                                         @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                         @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                         @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                         @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return followService.getAllFollowing(currentUser, pageable).stream()
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
