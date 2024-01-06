package com.elleined.forumapi.controller.user;

import com.elleined.forumapi.dto.UserDTO;
import com.elleined.forumapi.mapper.UserMapper;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDTO save(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.save(userDTO);
        return userMapper.toDTO(user);
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable("id") int id) {
        User user = userService.getById(id);
        return userMapper.toDTO(user);
    }

    @GetMapping("/{currentUserId}/suggested-mentions")
    public List<UserDTO> getSuggestedMentions(@PathVariable("currentUserId") int currentUserId,
                                              @RequestParam("name") String name) {
        User currentUser = userService.getById(currentUserId);
        return userService.getSuggestedMentions(currentUser, name).stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @GetMapping("/uuid/{uuid}")
    public UserDTO getByUUID(@PathVariable("uuid") String uuid) {
        User user = userService.getByUUID(uuid);
        return userMapper.toDTO(user);
    }
}
