package com.elleined.socialmediaapi.controller.user.story;

import com.elleined.socialmediaapi.dto.reaction.ReactionDTO;
import com.elleined.socialmediaapi.mapper.react.ReactionMapper;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.Reaction;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.emoji.EmojiService;
import com.elleined.socialmediaapi.service.reaction.ReactionService;
import com.elleined.socialmediaapi.service.story.StoryService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/stories/{storyId}/reactions")
public class StoryReactionController {
    private final UserService userService;

    private final StoryService storyService;

    private final ReactionService reactionService;
    private final ReactionMapper reactionMapper;

    private final EmojiService emojiService;

    @GetMapping
    public List<ReactionDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                    @PathVariable("storyId") int storyId,
                                    @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                    @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                    @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                    @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Story story = storyService.getById(storyId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return reactionService.getAll(currentUser, story, pageable).stream()
                .map(reactionMapper::toDTO)
                .toList();
    }

    @GetMapping("/emoji")
    public List<ReactionDTO> getAllByEmoji(@PathVariable("currentUserId") int currentUserId,
                                           @PathVariable("storyId") int storyId,
                                           @RequestParam("emojiId") int emojiId,
                                           @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                           @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                           @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                           @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Story story = storyService.getById(storyId);
        Emoji emoji = emojiService.getById(emojiId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return reactionService.getAllByEmoji(currentUser, story, emoji, pageable).stream()
                .map(reactionMapper::toDTO)
                .toList();
    }

    @PostMapping
    public ReactionDTO save(@PathVariable("currentUserId") int currentUserId,
                            @PathVariable("storyId") int storyId,
                            @RequestParam("emojiId") int emojiId) {

        User currentUser = userService.getById(currentUserId);
        Story story = storyService.getById(storyId);
        Emoji emoji = emojiService.getById(emojiId);

        if (reactionService.isAlreadyReactedTo(currentUser, story)) {
            Reaction reaction = reactionService.getByUserReaction(currentUser, story);
            reactionService.update(currentUser, story, reaction, emoji);
            return reactionMapper.toDTO(reaction);
        }

        Reaction reaction = reactionService.save(currentUser, story, emoji);
        return reactionMapper.toDTO(reaction);
    }

    @DeleteMapping("/{reactionId}")
    public void delete(@PathVariable("currentUserId") int currentUserId,
                       @PathVariable("storyId") int storyId,
                       @PathVariable("reactionId") int reactionId) {

        User currentUser = userService.getById(currentUserId);
        Story story = storyService.getById(storyId);
        Reaction reaction = reactionService.getById(reactionId);

        reactionService.delete(currentUser, story, reaction);
    }
}
