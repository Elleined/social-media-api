package com.elleined.socialmediaapi.controller.block;

import com.elleined.socialmediaapi.dto.APIResponse;
import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/blocked-users")
public class BlockController {
    private final BlockService blockService;

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public Set<UserDTO> getAllBlockedUserOf(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return blockService.getAllBlockedUsers(currentUser).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @GetMapping("/check/{userToCheckId}/blocked-by-you")
    public boolean isBlockedBy(@PathVariable("currentUserId") int currentUserId,
                               @PathVariable("userToCheckId") int userToCheckId) {

        User currentUser = userService.getById(currentUserId);
        User userToCheck = userService.getById(userToCheckId);

        return blockService.isBlockedBy(currentUser, userToCheck);
    }

    @GetMapping("/check/{suspectedBlockerId}/you-been-blocked")
    public boolean isYouBeenBlockedBy(@PathVariable("currentUserId") int currentUserId,
                                      @PathVariable("suspectedBlockerId") int suspectedBlockerId) {

        User currentUser = userService.getById(currentUserId);
        User suspectedBlocker = userService.getById(suspectedBlockerId);
        return blockService.isYouBeenBlockedBy(currentUser, suspectedBlocker);
    }

    @PatchMapping("{userToBeBlockedId}/block")
    public void blockUser(@PathVariable("currentUserId") int currentUserId,
                                 @PathVariable("userToBeBlockedId") int userToBeBlockedId) {

        User currentUser = userService.getById(currentUserId);
        User userToBeBlocked = userService.getById(userToBeBlockedId);

        blockService.blockUser(currentUser, userToBeBlocked);
    }

    @PatchMapping("/{userToBeUnblockedId}/unblock")
    public void unblockUser(@PathVariable("currentUserId") int currentUserId,
                                   @PathVariable("userToBeUnblockedId") int userToBeUnblockedId) {

        User currentUser = userService.getById(currentUserId);
        User userToBeUnblocked = userService.getById(userToBeUnblockedId);

        blockService.unBlockUser(currentUser, userToBeUnblocked);
    }
}
