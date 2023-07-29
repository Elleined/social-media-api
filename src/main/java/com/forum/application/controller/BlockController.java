package com.forum.application.controller;

import com.forum.application.dto.UserDTO;
import com.forum.application.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}")
public class BlockController {
    private final ForumService forumService;

    @PatchMapping("/blockUser/{userToBeBlockedId}")
    public ResponseEntity<String> blockUser(@PathVariable("currentUserId") int currentUserId,
                                            @PathVariable("userToBeBlockedId") int userToBeBlockedId) {

        forumService.blockUser(currentUserId, userToBeBlockedId);
        return ResponseEntity.ok("User with id of " + userToBeBlockedId + " blocked successfully");
    }

    @PatchMapping("/unblockUser/{userToBeUnblockedId}")
    public ResponseEntity<String> unblockUser(@PathVariable("currentUserId") int currentUserId,
                                              @PathVariable("userToBeUnblockedId") int userToBeUnblockedId) {
        forumService.unBlockUser(currentUserId, userToBeUnblockedId);
        return ResponseEntity.ok("User with id of " + userToBeUnblockedId + " unblocked successfully");
    }

    @GetMapping("/isBlockedBy/{userToCheckId}")
    public boolean isBlockedBy(@PathVariable("currentUserId") int currentUserId,
                               @PathVariable("userToCheckId") int userToCheckId) {
        return forumService.isBlockedBy(currentUserId, userToCheckId);
    }

    @GetMapping("/isYouBeenBlockedBy/{suspectedBlockerId}")
    public boolean isYouBeenBlockedBy(@PathVariable("currentUserId") int currentUserId,
                                      @PathVariable("suspectedBlockerId") int suspectedBlockerId) {
        return forumService.isYouBeenBlockedBy(currentUserId, suspectedBlockerId);
    }

    @GetMapping("/getAllBlockedUsers")
    public Set<UserDTO> getAllBlockedUserOf(@PathVariable("currentUserId") int currentUserId) {
        return forumService.getAllBlockedUsers(currentUserId);
    }
}
