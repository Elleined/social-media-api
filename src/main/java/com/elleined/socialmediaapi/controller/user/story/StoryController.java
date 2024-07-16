package com.elleined.socialmediaapi.controller.user.story;

import com.elleined.socialmediaapi.dto.story.StoryDTO;
import com.elleined.socialmediaapi.mapper.story.StoryMapper;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.service.story.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stories")
public class StoryController {
    private final StoryService storyService;
    private final StoryMapper storyMapper;

    @GetMapping
    public Page<StoryDTO> getAll(@RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                 @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                 @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                 @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);
        return storyService.getAll(pageable)
                .map(storyMapper::toDTO);
    }

    @GetMapping("/{id}")
    public StoryDTO getById(@PathVariable("id") int id) {
        Story story = storyService.getById(id);
        return storyMapper.toDTO(story);
    }
}
