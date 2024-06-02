package com.elleined.socialmediaapi.controller.notification.follow;

import com.elleined.socialmediaapi.dto.notification.follow.FollowerNotificationDTO;
import com.elleined.socialmediaapi.mapper.notification.follow.FollowerNotificationMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.follow.FollowerNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.follow.FollowerNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{currentUserId}/follower-notifications")
public class FollowerNotificationController {
    private final UserService userService;

    private final FollowerNotificationService followerNotificationService;
    private final FollowerNotificationMapper followerNotificationMapper;

    @GetMapping
    public List<FollowerNotificationDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                                @RequestParam("status") Notification.Status status,
                                                @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                                @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                                @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return followerNotificationService.getAll(currentUser, status, pageable).stream()
                .map(followerNotificationMapper::toDTO)
                .toList();
    }

    @PostMapping("/{id}/read")
    public void read(@PathVariable("currentUserId") int currentUserId,
                     @PathVariable("id") int id) {

        User currentUser = userService.getById(currentUserId);
        FollowerNotification commentMentionNotification = followerNotificationService.getById(id);

        followerNotificationService.read(currentUser, commentMentionNotification);
    }
}
