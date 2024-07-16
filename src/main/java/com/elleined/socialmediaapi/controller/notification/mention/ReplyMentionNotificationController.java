package com.elleined.socialmediaapi.controller.notification.mention;

import com.elleined.socialmediaapi.dto.notification.mention.ReplyMentionNotificationDTO;
import com.elleined.socialmediaapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.mention.ReplyMentionNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.mention.MentionNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{currentUserId}/reply-mention-notifications")
public class ReplyMentionNotificationController {
    private final UserService userService;

    private final MentionNotificationService<ReplyMentionNotification, Reply> notificationService;
    private final MentionNotificationMapper mentionNotificationMapper;

    @GetMapping
    public Page<ReplyMentionNotificationDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                                    @RequestParam("status") Notification.Status status,
                                                    @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                    @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                                    @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                                    @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return notificationService.getAll(currentUser, status, pageable)
                .map(mentionNotificationMapper::toDTO);
    }

    @PostMapping("/{id}/read")
    public void read(@PathVariable("currentUserId") int currentUserId,
                     @PathVariable("id") int id) {

        User currentUser = userService.getById(currentUserId);
        ReplyMentionNotification replyMentionNotification = notificationService.getById(id);

        notificationService.read(currentUser, replyMentionNotification);
    }
}
