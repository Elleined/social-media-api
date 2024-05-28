package com.elleined.socialmediaapi.controller.user;

import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.request.user.UserRequest;
import com.elleined.socialmediaapi.service.user.UserService;
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
    public UserDTO save(@Valid @RequestBody UserRequest userRequest) {
        User user = userService.save(userRequest);
        return userMapper.toDTO(user);
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable("id") int id) {
        User user = userService.getById(id);
        return userMapper.toDTO(user);
    }

    @GetMapping("/get-all-by-id")
    public List<UserDTO> getAllById(@RequestBody List<Integer> ids) {
        return userService.getAllById(ids).stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @GetMapping("/uuid/{uuid}")
    public UserDTO getByUUID(@PathVariable("uuid") String uuid) {
        User user = userService.getByUUID(uuid);
        return userMapper.toDTO(user);
    }

    @GetMapping("/{currentUserId}/mention/suggested-users")
    public List<UserDTO> getAllSuggestedMentions(@PathVariable("currentUserId") int currentUserId,
                                                 @RequestParam("name") String name) {

        User currentUser = userService.getById(currentUserId);
        return userService.getAllSuggestedMentions(currentUser, name, ).stream()
                .map(userMapper::toDTO)
                .toList();
    }
}
