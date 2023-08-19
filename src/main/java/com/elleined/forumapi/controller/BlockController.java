package com.elleined.forumapi.controller;

import com.elleined.forumapi.dto.ResponseMessage;
import com.elleined.forumapi.dto.UserDTO;
import com.elleined.forumapi.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/blocking/{currentUserId}")
@CrossOrigin(origins = "*") // Allow other ports to access these endpoints
public class BlockController {
    private final ForumService forumService;

    @PatchMapping("/blockUser/{userToBeBlockedId}")
    public ResponseMessage blockUser(@PathVariable("currentUserId") int currentUserId,
                                     @PathVariable("userToBeBlockedId") int userToBeBlockedId) {

        forumService.blockUser(currentUserId, userToBeBlockedId);
        return new ResponseMessage(HttpStatus.OK, "User with id of " + userToBeBlockedId + " blocked successfully");
    }

    @PatchMapping("/unblockUser/{userToBeUnblockedId}")
    public ResponseMessage unblockUser(@PathVariable("currentUserId") int currentUserId,
                              @PathVariable("userToBeUnblockedId") int userToBeUnblockedId) {
        forumService.unBlockUser(currentUserId, userToBeUnblockedId);
        return new ResponseMessage(HttpStatus.OK, "User with id of " + userToBeUnblockedId + " unblocked successfully");
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
