package com.elleined.socialmediaapi.controller.user;

import com.elleined.socialmediaapi.dto.UserDTO;
import com.elleined.socialmediaapi.mapper.UserMapper;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.service.UserService;
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
