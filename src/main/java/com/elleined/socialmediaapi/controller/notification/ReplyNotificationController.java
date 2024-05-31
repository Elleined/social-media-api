package com.elleined.socialmediaapi.controller.notification;

import com.elleined.socialmediaapi.dto.notification.main.ReplyNotificationDTO;
import com.elleined.socialmediaapi.mapper.notification.ReplyNotificationMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.main.ReplyNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.main.reply.ReplyNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{currentUserId}/reply-notifications")
public class ReplyNotificationController {
    private final UserService userService;

    private final ReplyNotificationService replyNotificationService;
    private final ReplyNotificationMapper replyNotificationMapper;

    @GetMapping
    public List<ReplyNotificationDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                             @RequestParam("status") Notification.Status status,
                                             @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                             @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                             @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                             @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return replyNotificationService.getAll(currentUser, status, pageable).stream()
                .map(replyNotificationMapper::toDTO)
                .toList();
    }

    @PostMapping("/{id}/read")
    public void read(@PathVariable("currentUserId") int currentUserId,
                     @PathVariable("id") int id) {

        User currentUser = userService.getById(currentUserId);
        ReplyNotification replyNotification = replyNotificationService.getById(id);

        replyNotificationService.read(currentUser, replyNotification);
    }
}
