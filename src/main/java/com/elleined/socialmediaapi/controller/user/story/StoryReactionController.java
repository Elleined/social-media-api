

package com.elleined.socialmediaapi.controller.user.story;

import com.elleined.socialmediaapi.dto.notification.reaction.StoryReactionNotificationDTO;
import com.elleined.socialmediaapi.dto.reaction.ReactionDTO;
import com.elleined.socialmediaapi.mapper.notification.reaction.ReactionNotificationMapper;
import com.elleined.socialmediaapi.mapper.react.ReactionMapper;
import com.elleined.socialmediaapi.model.notification.reaction.StoryReactionNotification;
import com.elleined.socialmediaapi.model.reaction.Emoji;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.emoji.EmojiService;
import com.elleined.socialmediaapi.service.notification.reaction.ReactionNotificationService;
import com.elleined.socialmediaapi.service.reaction.ReactionService;
import com.elleined.socialmediaapi.service.story.StoryService;
import com.elleined.socialmediaapi.service.user.UserService;
import com.elleined.socialmediaapi.ws.notification.NotificationWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/stories/{storyId}/reactions")
public class StoryReactionController {
    private final UserService userService;

    private final StoryService storyService;

    private final ReactionService reactionService;
    private final ReactionMapper reactionMapper;

    private final EmojiService emojiService;

    private final ReactionNotificationService<StoryReactionNotification, Story> reactionNotificationService;
    private final ReactionNotificationMapper reactionNotificationMapper;

    private final NotificationWSService notificationWSService;

    @GetMapping
    public Page<ReactionDTO> getAll(@RequestHeader("Authorization") String jwt,
                                    @PathVariable("storyId") int storyId,
                                    @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                    @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                    @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                    @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Story story = storyService.getById(storyId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return reactionService.getAll(currentUser, story, pageable)
                .map(reactionMapper::toDTO);
    }

    @GetMapping("/emoji")
    public Page<ReactionDTO> getAllByEmoji(@RequestHeader("Authorization") String jwt,
                                           @PathVariable("storyId") int storyId,
                                           @RequestParam("emojiId") int emojiId,
                                           @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                           @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                           @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                           @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Story story = storyService.getById(storyId);
        Emoji emoji = emojiService.getById(emojiId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return reactionService.getAllByEmoji(currentUser, story, emoji, pageable)
                .map(reactionMapper::toDTO);
    }

    @PostMapping
    public ReactionDTO save(@RequestHeader("Authorization") String jwt,
                            @PathVariable("storyId") int storyId,
                            @RequestParam("emojiId") int emojiId) {

        // Getting entities
        User currentUser = userService.getByJWT(jwt);
        Story story = storyService.getById(storyId);
        Emoji emoji = emojiService.getById(emojiId);

        if (reactionService.isAlreadyReactedTo(currentUser, story)) {
            Reaction reaction = reactionService.getByUserReaction(currentUser, story);
            reactionService.update(currentUser, story, reaction, emoji);
            return reactionMapper.toDTO(reaction);
        }

        // Saving entities
        Reaction reaction = reactionService.save(currentUser, story, emoji);
        StoryReactionNotification storyReactionNotification = reactionNotificationService.save(currentUser, story, reaction);

        // DTO Conversion
        StoryReactionNotificationDTO storyReactionNotificationDTO = reactionNotificationMapper.toDTO(storyReactionNotification);
        ReactionDTO reactionDTO = reactionMapper.toDTO(reaction);

        // Web Socket
        notificationWSService.notifyOnReaction(storyReactionNotificationDTO);

        return reactionDTO;
    }

    @DeleteMapping("/{reactionId}")
    public void delete(@RequestHeader("Authorization") String jwt,
                       @PathVariable("storyId") int storyId,
                       @PathVariable("reactionId") int reactionId) {

        User currentUser = userService.getByJWT(jwt);
        Story story = storyService.getById(storyId);
        Reaction reaction = reactionService.getById(reactionId);

        reactionService.delete(currentUser, story, reaction);
    }
}
