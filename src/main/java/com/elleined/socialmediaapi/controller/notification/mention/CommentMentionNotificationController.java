package com.elleined.socialmediaapi.controller.notification.mention;

import com.elleined.socialmediaapi.dto.notification.mention.CommentMentionNotificationDTO;
import com.elleined.socialmediaapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.mention.CommentMentionNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.mention.MentionNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/comment-mention-notifications")
public class CommentMentionNotificationController {
    private final UserService userService;

    private final MentionNotificationService<CommentMentionNotification, Comment> notificationService;
    private final MentionNotificationMapper mentionNotificationMapper;

    @GetMapping
    public Page<CommentMentionNotificationDTO> getAll(@RequestHeader("Authorization") String jwt,
                                                      @RequestParam("status") Notification.Status status,
                                                      @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                      @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                                      @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                                      @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return notificationService.getAll(currentUser, status, pageable)
                .map(mentionNotificationMapper::toDTO);
    }

    @PostMapping("/{id}/read")
    public void read(@RequestHeader("Authorization") String jwt,
                     @PathVariable("id") int id) {

        User currentUser = userService.getByJWT(jwt);
        CommentMentionNotification commentMentionNotification = notificationService.getById(id);

        notificationService.read(currentUser, commentMentionNotification);
    }
}
