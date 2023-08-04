package com.forum.application.controller;

import com.forum.application.dto.CommentDTO;
import com.forum.application.dto.ReplyDTO;
import com.forum.application.dto.notification.CommentNotification;
import com.forum.application.dto.notification.Notification;
import com.forum.application.dto.notification.ReplyNotification;
import com.forum.application.model.User;
import com.forum.application.service.NotificationService;
import com.forum.application.service.UserService;
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

    @PostMapping("/getCommentNotification")
    public CommentNotification getNotification(@RequestBody CommentDTO commentDTO) {
        return notificationService.getNotification(commentDTO);
    }

    @PostMapping("/getReplyNotification")
    public ReplyNotification getNotification(@RequestBody ReplyDTO replyDTO) {
        return notificationService.getNotification(replyDTO);
    }


}
