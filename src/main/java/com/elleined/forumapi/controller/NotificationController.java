package com.elleined.forumapi.controller;

import com.elleined.forumapi.dto.notification.Notification;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.NotificationService;
import com.elleined.forumapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/{currentUserId}/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final UserService userService;
    private final NotificationService notificationService;


    @GetMapping("/getAllNotification")
    public Set<Notification> getAllNotification(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return notificationService.getAllNotification(currentUser);
    }

    @GetMapping("/getTotalNotificationCount")
    public long getTotalNotificationCount(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return notificationService.getTotalNotificationCount(currentUser);
    }
}
