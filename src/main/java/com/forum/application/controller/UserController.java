package com.forum.application.controller;

import com.forum.application.dto.UserDTO;
import com.forum.application.service.ForumService;
import com.forum.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final ForumService forumService;

    private final UserService userService;
    @PostMapping
    public UserDTO save(@Valid @RequestBody UserDTO userDTO) {
        return forumService.saveUser(userDTO);
    }

    @GetMapping("/{currentUserId}/getAllUser")
    public List<UserDTO> getAllUser(@PathVariable("currentUserId") int currentUserId) {
        return forumService.getAllUser(currentUserId);
    }

    @GetMapping("/{currentUserId}/getSuggestedMentions")
    public List<UserDTO> getSuggestedMentions(@PathVariable("currentUserId") int currentUserId,
                                              @RequestParam("name") String name) {
        return forumService.getSuggestedMentions(currentUserId, name);
    }

    @GetMapping("/getByUUID/{UUID}")
    public UserDTO UserDTO(@PathVariable("UUID") String UUID) {
        return forumService.getByUUID(UUID);
    }

    @GetMapping("/{currentUserId}/getIdByEmail")
    public int getIdByEmail(@RequestParam("email") String email) {
        return userService.getIdByEmail(email);
    }

    @GetMapping("/{currentUserId}/isUserExists")
    public boolean isUserExists(@RequestParam("email") String email) {
        return userService.isEmailExists(email);
    }
}
