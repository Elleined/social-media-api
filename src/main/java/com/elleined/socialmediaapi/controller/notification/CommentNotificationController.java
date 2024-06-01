package com.elleined.socialmediaapi.controller.notification;

import com.elleined.socialmediaapi.dto.notification.main.CommentNotificationDTO;
import com.elleined.socialmediaapi.mapper.notification.main.CommentNotificationMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.main.CommentNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.main.comment.CommentNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{currentUserId}/comment-notifications")
public class CommentNotificationController {
    private final UserService userService;

    private final CommentNotificationService commentNotificationService;
    private final CommentNotificationMapper commentNotificationMapper;

    @GetMapping
    public List<CommentNotificationDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                               @RequestParam("status") Notification.Status status,
                                               @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                               @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                               @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                               @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return commentNotificationService.getAll(currentUser, status, pageable).stream()
                .map(commentNotificationMapper::toDTO)
                .toList();
    }

    @PostMapping("/{id}/read")
    public void read(@PathVariable("currentUserId") int currentUserId,
                     @PathVariable("id") int id) {

        User currentUser = userService.getById(currentUserId);
        CommentNotification commentNotification = commentNotificationService.getById(id);

        commentNotificationService.read(currentUser, commentNotification);
    }
}
