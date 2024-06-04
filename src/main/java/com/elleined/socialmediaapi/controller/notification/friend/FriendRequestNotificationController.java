package com.elleined.socialmediaapi.controller.notification.friend;


import com.elleined.socialmediaapi.dto.notification.friend.FriendRequestNotificationDTO;
import com.elleined.socialmediaapi.mapper.notification.friend.FriendRequestNotificationMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.friend.FriendRequestNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.friend.FriendRequestNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{currentUserId}/friend-request-notifications")
public class FriendRequestNotificationController {
    private final UserService userService;

    private final FriendRequestNotificationService friendRequestNotificationService;
    private final FriendRequestNotificationMapper friendRequestNotificationMapper;


    @GetMapping
    public List<FriendRequestNotificationDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                                     @RequestParam("status") Notification.Status status,
                                                     @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                     @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                                     @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                                     @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return friendRequestNotificationService.getAll(currentUser, status, pageable).stream()
                .map(friendRequestNotificationMapper::toDTO)
                .toList();
    }

    @PostMapping("/{id}/read")
    public void read(@PathVariable("currentUserId") int currentUserId,
                     @PathVariable("id") int id) {

        User currentUser = userService.getById(currentUserId);
        FriendRequestNotification commentMentionNotification = friendRequestNotificationService.getById(id);

        friendRequestNotificationService.read(currentUser, commentMentionNotification);
    }
}
