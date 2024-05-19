package com.elleined.socialmediaapi.controller.user.story;

import com.elleined.socialmediaapi.dto.story.StoryDTO;
import com.elleined.socialmediaapi.mapper.story.StoryMapper;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.request.story.StoryRequest;
import com.elleined.socialmediaapi.service.story.StoryService;
import com.elleined.socialmediaapi.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/stories")
public class StoryController {
    private final StoryService storyService;
    private final StoryMapper storyMapper;

    private final UserService userService;

    @GetMapping
    public List<StoryDTO> getAll() {
        return storyService.getAll().stream()
                .map(storyMapper::toDTO)
                .toList();
    }

    @GetMapping("/user")
    public StoryDTO getStory(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        Story story = storyService.getStory(currentUser);
        return storyMapper.toDTO(story);
    }

    @GetMapping("/{id}")
    public StoryDTO getById(@PathVariable("id") int id) {
        Story story = storyService.getById(id);
        return storyMapper.toDTO(story);
    }

    @GetMapping("/get-all-by-id")
    public List<StoryDTO> getAllById(@RequestBody List<Integer> ids) {
        return storyService.getAllById(ids).stream()
                .map(storyMapper::toDTO)
                .toList();
    }

    @PostMapping
    public StoryDTO save(@PathVariable("currentUserId") int currentUserId,
                         @Valid @RequestBody StoryRequest storyRequest) {
        User currentUser = userService.getById(currentUserId);
        Story story = storyService.save(currentUser, storyRequest);
        return storyMapper.toDTO(story);
    }

    @DeleteMapping("/{storyId}")
    public void delete(@PathVariable("currentUserId") int currentUserId,
                       @PathVariable("storyId") int storyId) {
        User currentUser = userService.getById(currentUserId);
        Story story = storyService.getById(storyId);
        storyService.delete(currentUser, story);
    }
}
