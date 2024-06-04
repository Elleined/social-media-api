package com.elleined.socialmediaapi.controller.user.friend;

import com.elleined.socialmediaapi.dto.friend.FriendRequestDTO;
import com.elleined.socialmediaapi.dto.notification.friend.FriendRequestNotificationDTO;
import com.elleined.socialmediaapi.mapper.friend.FriendRequestMapper;
import com.elleined.socialmediaapi.mapper.notification.friend.FriendRequestNotificationMapper;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.notification.friend.FriendRequestNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.friend.FriendService;
import com.elleined.socialmediaapi.service.notification.friend.FriendRequestNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
import com.elleined.socialmediaapi.ws.notification.NotificationWSService;
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

    private final FriendRequestNotificationService friendRequestNotificationService;
    private final FriendRequestNotificationMapper friendRequestNotificationMapper;

    private final NotificationWSService notificationWSService;

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

        // Getting entities
        User currentUser = userService.getById(currentUserId);
        User userToAdd = userService.getById(userToAddId);

        // Saving entities
        FriendRequest friendRequest = friendService.sendFriendRequest(currentUser, userToAdd);
        FriendRequestNotification friendRequestNotification = friendRequestNotificationService.save(currentUser, userToAdd, STR."\{currentUser.getName()} sent you a friend request", friendRequest);

        // DTO Conversion
        FriendRequestNotificationDTO friendRequestNotificationDTO = friendRequestNotificationMapper.toDTO(friendRequestNotification);

        // Web Socket
        notificationWSService.notifyOnReceiveFriendRequest(friendRequestNotificationDTO);
    }

    @PatchMapping("/{friendRequestId}/accept")
    public void acceptFriendRequest(@PathVariable("currentUserId") int currentUserId,
                                    @PathVariable("friendRequestId") int friendRequestId) {

        // Getting entities
        User currentUser = userService.getById(currentUserId);
        FriendRequest friendRequest = friendService.getById(friendRequestId);
        User receiver = friendRequest.getCreator();

        // Saving entities
        friendService.acceptFriendRequest(currentUser, friendRequest);
        FriendRequestNotification friendRequestNotification = friendRequestNotificationService.save(currentUser, receiver, STR."\{receiver.getName()} accepted your friend request.", friendRequest);

        // DTO Conversion
        FriendRequestNotificationDTO friendRequestNotificationDTO = friendRequestNotificationMapper.toDTO(friendRequestNotification);

        // Web Socket
        notificationWSService.notifyOnAcceptFriendRequest(friendRequestNotificationDTO);
    }

    @DeleteMapping("/{friendRequestId}/reject")
    public void rejectFriendRequest(@PathVariable("currentUserId") int currentUserId,
                                    @PathVariable("friendRequestId") int friendRequestId) {
        User currentUser = userService.getById(currentUserId);
        FriendRequest friendRequest = friendService.getById(friendRequestId);
        friendService.rejectFriendRequest(currentUser, friendRequest);
    }
}
