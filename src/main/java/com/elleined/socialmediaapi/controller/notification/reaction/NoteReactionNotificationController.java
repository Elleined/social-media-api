package com.elleined.socialmediaapi.controller.notification.reaction;

import com.elleined.socialmediaapi.dto.notification.reaction.NoteReactionNotificationDTO;
import com.elleined.socialmediaapi.mapper.notification.reaction.ReactionNotificationMapper;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.reaction.NoteReactionNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.reaction.ReactionNotificationService;
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
@RequestMapping("/users/{currentUserId}/note-reaction-notifications")
public class NoteReactionNotificationController {

    private final UserService userService;

    private final ReactionNotificationService<NoteReactionNotification, Note> reactionNotificationService;
    private final ReactionNotificationMapper reactionNotificationMapper;

    @GetMapping
    public Page<NoteReactionNotificationDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                                    @RequestParam("status") Notification.Status status,
                                                    @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                                    @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                                    @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                                    @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return reactionNotificationService.getAll(currentUser, status, pageable)
                .map(reactionNotificationMapper::toDTO);
    }

    @PostMapping("/{id}/read")
    public void read(@PathVariable("currentUserId") int currentUserId,
                     @PathVariable("id") int id) {

        User currentUser = userService.getById(currentUserId);
        NoteReactionNotification noteReactionNotification = reactionNotificationService.getById(id);

        reactionNotificationService.read(currentUser, noteReactionNotification);
    }
}
