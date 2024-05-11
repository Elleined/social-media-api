package com.elleined.socialmediaapi.controller;

import com.elleined.socialmediaapi.dto.ResponseMessage;
import com.elleined.socialmediaapi.dto.UserDTO;
import com.elleined.socialmediaapi.mapper.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.UserService;
import com.elleined.socialmediaapi.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}")
public class BlockController {
    private final BlockService blockService;

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/block")
    public Set<UserDTO> getAllBlockedUserOf(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return blockService.getAllBlockedUsers(currentUser).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @PatchMapping("/block/{userToBeBlockedId}")
    public ResponseMessage blockUser(@PathVariable("currentUserId") int currentUserId,
                                     @PathVariable("userToBeBlockedId") int userToBeBlockedId) {

        User currentUser = userService.getById(currentUserId);
        User userToBeBlocked = userService.getById(userToBeBlockedId);

        blockService.blockUser(currentUser, userToBeBlocked);
        return new ResponseMessage(HttpStatus.OK, "User with id of " + userToBeBlockedId + " blocked successfully");
    }

    @PatchMapping("/unblock/{userToBeUnblockedId}")
    public ResponseMessage unblockUser(@PathVariable("currentUserId") int currentUserId,
                                       @PathVariable("userToBeUnblockedId") int userToBeUnblockedId) {

        User currentUser = userService.getById(currentUserId);
        User userToBeUnblocked = userService.getById(userToBeUnblockedId);

        blockService.unBlockUser(currentUser, userToBeUnblocked);
        return new ResponseMessage(HttpStatus.OK, "User with id of " + userToBeUnblockedId + " unblocked successfully");
    }

    @GetMapping("/is-blocked-by/{userToCheckId}")
    public boolean isBlockedBy(@PathVariable("currentUserId") int currentUserId,
                               @PathVariable("userToCheckId") int userToCheckId) {

        User currentUser = userService.getById(currentUserId);
        User userToCheck = userService.getById(userToCheckId);

        return blockService.isBlockedBy(currentUser, userToCheck);
    }

    @GetMapping("/is-you-been-blocked-by/{suspectedBlockerId}")
    public boolean isYouBeenBlockedBy(@PathVariable("currentUserId") int currentUserId,
                                      @PathVariable("suspectedBlockerId") int suspectedBlockerId) {

        User currentUser = userService.getById(currentUserId);
        User suspectedBlocker = userService.getById(suspectedBlockerId);
        return blockService.isYouBeenBlockedBy(currentUser, suspectedBlocker);
    }
}
