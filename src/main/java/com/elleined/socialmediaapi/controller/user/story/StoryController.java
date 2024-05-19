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
@RequestMapping("/stories")
public class StoryController {
    private final StoryService storyService;
    private final StoryMapper storyMapper;

    @GetMapping
    public List<StoryDTO> getAll() {
        return storyService.getAll().stream()
                .map(storyMapper::toDTO)
                .toList();
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
}
