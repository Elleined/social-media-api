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

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/story")
public class UserStoryController {
    private final UserService userService;

    private final StoryService storyService;
    private final StoryMapper storyMapper;

    @GetMapping
    public StoryDTO getStory(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        Story story = storyService.getStory(currentUser);
        return storyMapper.toDTO(story);
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
