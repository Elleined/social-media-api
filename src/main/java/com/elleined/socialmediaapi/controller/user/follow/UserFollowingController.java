package com.elleined.socialmediaapi.controller.user.follow;

import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.follow.FollowService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users/followings")
@RequiredArgsConstructor
public class UserFollowingController {
    private final UserService userService;
    private final UserMapper userMapper;

    private final FollowService followService;

    @GetMapping
    public Page<UserDTO> getAllFollowing(@RequestHeader("Authorization") String jwt,
                                         @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                         @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                         @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                         @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return followService.getAllFollowing(currentUser, pageable)
                .map(userMapper::toDTO);
    }

    @PostMapping("/{userToFollowId}")
    public void follow(@RequestHeader("Authorization") String jwt,
                       @PathVariable("userToFollowId") int userToFollowId) {
        User currentUser = userService.getByJWT(jwt);
        User userToFollow = userService.getById(userToFollowId);

        followService.follow(currentUser, userToFollow);
    }

    @DeleteMapping("/{userToUnFollowId}")
    public void unFollow(@RequestHeader("Authorization") String jwt,
                         @PathVariable("userToUnFollowId") int userToUnFollowId) {
        User currentUser = userService.getByJWT(jwt);
        User userToUnFollow = userService.getById(userToUnFollowId);

        followService.unFollow(currentUser, userToUnFollow);
    }
}
