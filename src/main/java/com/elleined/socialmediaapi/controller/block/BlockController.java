package com.elleined.socialmediaapi.controller.block;

import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Set<UserDTO> getAllBlockedUsers(@PathVariable("currentUserId") int currentUserId,
                                           @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                           @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                           @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                           @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return blockService.getAllBlockedUsers(currentUser, pageable).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @GetMapping("/is-blocked/{otherUserId}")
    public boolean isBlocked(@PathVariable("currentUserId") int currentUserId,
                               @PathVariable("otherUserId") int otherUserId) {

        User currentUser = userService.getById(currentUserId);
        User otherUser = userService.getById(otherUserId);

        return blockService.isBlockedByYou(currentUser, otherUser) ||
                blockService.isYouBeenBlockedBy(currentUser, otherUser);
    }

    @PatchMapping("/block/{userToBeBlockedId}")
    public void blockUser(@PathVariable("currentUserId") int currentUserId,
                          @PathVariable("userToBeBlockedId") int userToBeBlockedId) {

        User currentUser = userService.getById(currentUserId);
        User userToBeBlocked = userService.getById(userToBeBlockedId);

        blockService.blockUser(currentUser, userToBeBlocked);
    }

    @PatchMapping("/unblock/{userToBeUnblockedId}")
    public void unblockUser(@PathVariable("currentUserId") int currentUserId,
                            @PathVariable("userToBeUnblockedId") int userToBeUnblockedId) {

        User currentUser = userService.getById(currentUserId);
        User userToBeUnblocked = userService.getById(userToBeUnblockedId);

        blockService.unBlockUser(currentUser, userToBeUnblocked);
    }
}
