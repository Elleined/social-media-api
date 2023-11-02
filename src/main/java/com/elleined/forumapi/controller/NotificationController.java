package com.elleined.forumapi.controller;

import com.elleined.forumapi.dto.notification.Notification;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/{currentUserId}/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final UserService userService;
    private final NotificationService<Notification> notificationService;


    @GetMapping("/getAllNotification")
    public Set<Notification> getAllNotification(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return notificationService.getAllUnreadNotification(currentUser);
    }

    @GetMapping("/getTotalNotificationCount")
    public long getTotalNotificationCount(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return notificationService.getTotalNotificationCount(currentUser);
    }
}
