package com.elleined.socialmediaapi.controller.notification.vote;

import com.elleined.socialmediaapi.dto.notification.vote.VoteNotificationDTO;
import com.elleined.socialmediaapi.mapper.notification.vote.VoteNotificationMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.vote.VoteNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.vote.VoteNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/vote-notifications")
public class VoteNotificationController {
    private final UserService userService;

    private final VoteNotificationService voteNotificationService;
    private final VoteNotificationMapper voteNotificationMapper;

    @GetMapping
    public Page<VoteNotificationDTO> getAll(@RequestHeader("Authorization") String jwt,
                                            @RequestParam("status") Notification.Status status,
                                            @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                            @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                            @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                            @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return voteNotificationService.getAll(currentUser, status, pageable)
                .map(voteNotificationMapper::toDTO);
    }

    @PostMapping("/{id}/read")
    public void read(@RequestHeader("Authorization") String jwt,
                     @PathVariable("id") int id) {

        User currentUser = userService.getByJWT(jwt);
        VoteNotification commentMentionNotification = voteNotificationService.getById(id);

        voteNotificationService.read(currentUser, commentMentionNotification);
    }
}
