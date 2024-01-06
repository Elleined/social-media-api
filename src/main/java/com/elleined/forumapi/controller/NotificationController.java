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

import java.util.Collection;

@RestController
@RequestMapping("/users/{currentUserId}/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final UserService userService;
    private final NotificationService<Notification> notificationService;

    @GetMapping
    public Collection<Notification> getAllUnreadNotification(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return notificationService.getAllUnreadNotification(currentUser);
    }

    @GetMapping("/count")
    public long getNotificationCount(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return notificationService.getNotificationCount(currentUser);
    }
}
