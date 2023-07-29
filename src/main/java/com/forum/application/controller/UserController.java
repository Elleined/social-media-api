package com.forum.application.controller;

import com.forum.application.dto.UserDTO;
import com.forum.application.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{currentUserId}")
public class UserController {
    private final ForumService forumService;

    @GetMapping
    public List<UserDTO> getAllUser(@PathVariable("currentUserId") int currentUserId) {
        return forumService.getAllUser(currentUserId);
    }

    @GetMapping("/getSuggestedMentions")
    public List<UserDTO> getSuggestedMentions(@PathVariable("currentUserId") int currentUserId,
                                              @RequestParam("name") String name) {
        return forumService.getSuggestedMentions(currentUserId, name);
    }
}
