package com.elleined.socialmediaapi.controller.notification.friend;


import com.elleined.socialmediaapi.dto.notification.friend.FriendRequestNotificationDTO;
import com.elleined.socialmediaapi.mapper.notification.friend.FriendRequestNotificationMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.friend.FriendRequestNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.friend.FriendRequestNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/friend-request-notifications")
public class FriendRequestNotificationController {
    private final UserService userService;

    private final FriendRequestNotificationService friendRequestNotificationService;
    private final FriendRequestNotificationMapper friendRequestNotificationMapper;


    @GetMapping
    public Page<FriendRequestNotificationDTO> getAll(@RequestHeader("Authorization") String jwt,
                                                     @RequestParam("status") Notification.Status status,
                                                     @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                     @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                                     @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                                     @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return friendRequestNotificationService.getAll(currentUser, status, pageable)
                .map(friendRequestNotificationMapper::toDTO);
    }

    @PostMapping("/{id}/read")
    public void read(@RequestHeader("Authorization") String jwt,
                     @PathVariable("id") int id) {

        User currentUser = userService.getByJWT(jwt);
        FriendRequestNotification commentMentionNotification = friendRequestNotificationService.getById(id);

        friendRequestNotificationService.read(currentUser, commentMentionNotification);
    }
}
