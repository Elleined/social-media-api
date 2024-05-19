package com.elleined.socialmediaapi.controller.block;

import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/blocked-users")
    public Set<UserDTO> getAllBlockedUserOf(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return blockService.getAllBlockedUsers(currentUser).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @GetMapping("/blocked-by-you/{userToCheckId}")
    public boolean isBlockedBy(@PathVariable("currentUserId") int currentUserId,
                               @PathVariable("userToCheckId") int userToCheckId) {

        User currentUser = userService.getById(currentUserId);
        User userToCheck = userService.getById(userToCheckId);

        return blockService.isBlockedByYou(currentUser, userToCheck);
    }

    @GetMapping("/you-been-blocked-by/{suspectedBlockerId}")
    public boolean isYouBeenBlockedBy(@PathVariable("currentUserId") int currentUserId,
                                      @PathVariable("suspectedBlockerId") int suspectedBlockerId) {

        User currentUser = userService.getById(currentUserId);
        User suspectedBlocker = userService.getById(suspectedBlockerId);
        return blockService.isYouBeenBlockedBy(currentUser, suspectedBlocker);
    }

    @PatchMapping("/{userToBeBlockedId}/block")
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
