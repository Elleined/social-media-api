package com.elleined.socialmediaapi.controller.notification.post;

import com.elleined.socialmediaapi.dto.notification.post.SharedPostNotificationDTO;
import com.elleined.socialmediaapi.mapper.notification.post.SharedPostNotificationMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.post.SharedPostNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.post.SharedPostNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/shared-post-notifications")
public class SharedPostNotificationController {
    private final UserService userService;

    private final SharedPostNotificationService sharedPostNotificationService;
    private final SharedPostNotificationMapper sharedPostNotificationMapper;

    @GetMapping
    public Page<SharedPostNotificationDTO> getAll(@RequestHeader("Authorization") String jwt,
                                                  @RequestParam("status") Notification.Status status,
                                                  @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                  @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                                  @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                                  @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return sharedPostNotificationService.getAll(currentUser, status, pageable)
                .map(sharedPostNotificationMapper::toDTO);
    }

    @PostMapping("/{id}/read")
    public void read(@RequestHeader("Authorization") String jwt,
                     @PathVariable("id") int id) {

        User currentUser = userService.getByJWT(jwt);
        SharedPostNotification commentMentionNotification = sharedPostNotificationService.getById(id);

        sharedPostNotificationService.read(currentUser, commentMentionNotification);
    }
}
