package com.elleined.socialmediaapi.controller.notification;

import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/{currentUserId}/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final UserService userService;
    private final NotificationService notificationService;

    @GetMapping
    public List<Notification> getAllUnreadNotification(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return notificationService.getAllUnreadNotification(currentUser);
    }
}
