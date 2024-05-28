package com.elleined.socialmediaapi.controller.user.friend;

import com.elleined.socialmediaapi.dto.friend.FriendRequestDTO;
import com.elleined.socialmediaapi.mapper.friend.FriendRequestMapper;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.friend.FriendService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/friend-requests")
public class FriendRequestController {

    private final FriendService friendService;
    private final UserService userService;

    private final FriendRequestMapper friendRequestMapper;

    @GetMapping
    public List<FriendRequestDTO> getAllFriendRequests(@PathVariable("currentUserId") int currentUserId,
                                                       @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                       @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                                       @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                                       @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return friendService.getAllFriendRequests(currentUser, pageable).stream()
                .map(friendRequestMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public FriendRequestDTO getById(@PathVariable("id") int id) {
        FriendRequest friendRequest = friendService.getById(id);
        return friendRequestMapper.toDTO(friendRequest);
    }

    @GetMapping("/get-all-by-id")
    public List<FriendRequestDTO> getAllById(@RequestBody List<Integer> ids) {
        return friendService.getAllById(ids).stream()
                .map(friendRequestMapper::toDTO)
                .toList();
    }

    @PostMapping("/{userToAddId}/send")
    public void sendFriendRequest(@PathVariable("currentUserId") int currentUserId,
                                  @PathVariable("userToAddId") int userToAddId) {
        User currentUser = userService.getById(currentUserId);
        User userToAdd = userService.getById(userToAddId);
        friendService.sendFriendRequest(currentUser, userToAdd);
    }

    @PatchMapping("/{friendRequestId}/accept")
    public void acceptFriendRequest(@PathVariable("currentUserId") int currentUserId,
                                    @PathVariable("friendRequestId") int friendRequestId) {
        User currentUser = userService.getById(currentUserId);
        FriendRequest friendRequest = friendService.getById(friendRequestId);
        friendService.acceptFriendRequest(currentUser, friendRequest);
    }

    @DeleteMapping("/{friendRequestId}/reject")
    public void rejectFriendRequest(@PathVariable("currentUserId") int currentUserId,
                                    @PathVariable("friendRequestId") int friendRequestId) {
        User currentUser = userService.getById(currentUserId);
        FriendRequest friendRequest = friendService.getById(friendRequestId);
        friendService.rejectFriendRequest(currentUser, friendRequest);
    }
}
