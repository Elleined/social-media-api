package com.elleined.socialmediaapi.controller.notification.main;

import com.elleined.socialmediaapi.dto.notification.main.CommentNotificationDTO;
import com.elleined.socialmediaapi.mapper.notification.main.CommentNotificationMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.main.CommentNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.main.comment.CommentNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/comment-notifications")
public class CommentNotificationController {
    private final UserService userService;

    private final CommentNotificationService commentNotificationService;
    private final CommentNotificationMapper commentNotificationMapper;

    @GetMapping
    public Page<CommentNotificationDTO> getAll(@RequestHeader("Authorization") String jwt,
                                               @RequestParam("status") Notification.Status status,
                                               @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                               @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                               @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                               @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return commentNotificationService.getAll(currentUser, status, pageable)
                .map(commentNotificationMapper::toDTO);
    }

    @PostMapping("/{id}/read")
    public void read(@RequestHeader("Authorization") String jwt,
                     @PathVariable("id") int id) {

        User currentUser = userService.getByJWT(jwt);
        CommentNotification commentNotification = commentNotificationService.getById(id);

        commentNotificationService.read(currentUser, commentNotification);
    }
}
