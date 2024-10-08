package com.elleined.socialmediaapi.controller.notification.main;

import com.elleined.socialmediaapi.dto.notification.main.ReplyNotificationDTO;
import com.elleined.socialmediaapi.mapper.notification.main.ReplyNotificationMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.main.ReplyNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.main.reply.ReplyNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/reply-notifications")
public class ReplyNotificationController {
    private final UserService userService;

    private final ReplyNotificationService replyNotificationService;
    private final ReplyNotificationMapper replyNotificationMapper;

    @GetMapping
    public Page<ReplyNotificationDTO> getAll(@RequestHeader("Authorization") String jwt,
                                             @RequestParam("status") Notification.Status status,
                                             @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                             @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                             @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                             @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return replyNotificationService.getAll(currentUser, status, pageable)
                .map(replyNotificationMapper::toDTO);
    }

    @PostMapping("/{id}/read")
    public void read(@RequestHeader("Authorization") String jwt,
                     @PathVariable("id") int id) {

        User currentUser = userService.getByJWT(jwt);
        ReplyNotification replyNotification = replyNotificationService.getById(id);

        replyNotificationService.read(currentUser, replyNotification);
    }
}
