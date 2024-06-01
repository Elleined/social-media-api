package com.elleined.socialmediaapi.controller.user.story;

import com.elleined.socialmediaapi.dto.story.StoryDTO;
import com.elleined.socialmediaapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.socialmediaapi.mapper.story.StoryMapper;
import com.elleined.socialmediaapi.model.notification.mention.StoryMentionNotification;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.service.mention.MentionService;
import com.elleined.socialmediaapi.service.notification.mention.MentionNotificationService;
import com.elleined.socialmediaapi.service.story.StoryService;
import com.elleined.socialmediaapi.ws.notification.NotificationWSService;
import lombok.RequiredArgsConstructor;
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
    public List<StoryDTO> getAll(@RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                 @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                 @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                 @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);
        return storyService.getAll(pageable).stream()
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
