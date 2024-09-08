package com.elleined.socialmediaapi.controller.block;

import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class BlockController {
    private final BlockService blockService;

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/blocked-users")
    public Page<UserDTO> getAllBlockedUsers(@RequestHeader("Authorization") String jwt,
                                            @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                            @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                            @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                            @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return blockService.getAllBlockedUsers(currentUser, pageable)
                .map(userMapper::toDTO);
    }

    @GetMapping("/is-blocked/{otherUserId}")
    public boolean isBlocked(@RequestHeader("Authorization") String jwt,
                             @PathVariable("otherUserId") int otherUserId) {

        User currentUser = userService.getByJWT(jwt);
        User otherUser = userService.getById(otherUserId);

        return blockService.isBlockedByYou(currentUser, otherUser) ||
                blockService.isYouBeenBlockedBy(currentUser, otherUser);
    }

    @PatchMapping("/block/{userToBeBlockedId}")
    public void blockUser(@RequestHeader("Authorization") String jwt,
                          @PathVariable("userToBeBlockedId") int userToBeBlockedId) {

        User currentUser = userService.getByJWT(jwt);
        User userToBeBlocked = userService.getById(userToBeBlockedId);

        blockService.blockUser(currentUser, userToBeBlocked);
    }

    @PatchMapping("/unblock/{userToBeUnblockedId}")
    public void unblockUser(@RequestHeader("Authorization") String jwt,
                            @PathVariable("userToBeUnblockedId") int userToBeUnblockedId) {

        User currentUser = userService.getByJWT(jwt);
        User userToBeUnblocked = userService.getById(userToBeUnblockedId);

        blockService.unBlockUser(currentUser, userToBeUnblocked);
    }
}
