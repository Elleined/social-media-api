package com.elleined.socialmediaapi.controller.user.note;

import com.elleined.socialmediaapi.dto.notification.reaction.NoteReactionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.reaction.StoryReactionNotificationDTO;
import com.elleined.socialmediaapi.dto.reaction.ReactionDTO;
import com.elleined.socialmediaapi.mapper.notification.reaction.ReactionNotificationMapper;
import com.elleined.socialmediaapi.mapper.react.ReactionMapper;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.notification.reaction.NoteReactionNotification;
import com.elleined.socialmediaapi.model.notification.reaction.StoryReactionNotification;
import com.elleined.socialmediaapi.model.reaction.Emoji;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.emoji.EmojiService;
import com.elleined.socialmediaapi.service.note.NoteService;
import com.elleined.socialmediaapi.service.notification.reaction.ReactionNotificationService;
import com.elleined.socialmediaapi.service.reaction.ReactionService;
import com.elleined.socialmediaapi.service.user.UserService;
import com.elleined.socialmediaapi.ws.notification.NotificationWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/note/{noteId}/reactions")
public class NoteReactionController {

    private final UserService userService;

    private final NoteService noteService;

    private final ReactionService reactionService;
    private final ReactionMapper reactionMapper;

    private final EmojiService emojiService;

    private final ReactionNotificationService<NoteReactionNotification, Note> reactionNotificationService;
    private final ReactionNotificationMapper reactionNotificationMapper;

    private final NotificationWSService notificationWSService;

    @GetMapping
    public List<ReactionDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                    @PathVariable("noteId") int noteId,
                                    @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                    @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                    @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                    @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Note note = noteService.getById(noteId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return reactionService.getAll(currentUser, note, pageable).stream()
                .map(reactionMapper::toDTO)
                .toList();
    }

    @GetMapping("/emoji")
    public List<ReactionDTO> getAllByEmoji(@PathVariable("currentUserId") int currentUserId,
                                           @PathVariable("noteId") int noteId,
                                           @RequestParam("emojiId") int emojiId,
                                           @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                           @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                           @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                           @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Note note = noteService.getById(noteId);
        Emoji emoji = emojiService.getById(emojiId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return reactionService.getAllByEmoji(currentUser, note, emoji, pageable).stream()
                .map(reactionMapper::toDTO)
                .toList();
    }

    @PostMapping
    public ReactionDTO save(@PathVariable("currentUserId") int currentUserId,
                            @PathVariable("noteId") int noteId,
                            @RequestParam("emojiId") int emojiId) {

        // Getting entities
        User currentUser = userService.getById(currentUserId);
        Note note = noteService.getById(noteId);
        Emoji emoji = emojiService.getById(emojiId);

        if (reactionService.isAlreadyReactedTo(currentUser, note)) {
            Reaction reaction = reactionService.getByUserReaction(currentUser, note);
            reactionService.update(currentUser, note, reaction, emoji);
            return reactionMapper.toDTO(reaction);
        }

        // Saving entities
        Reaction reaction = reactionService.save(currentUser, note, emoji);
        NoteReactionNotification noteReactionNotification = reactionNotificationService.save(currentUser, note, reaction);

        // DTO Conversion
        NoteReactionNotificationDTO noteReactionNotificationDTO = reactionNotificationMapper.toDTO(noteReactionNotification);
        ReactionDTO reactionDTO = reactionMapper.toDTO(reaction);

        // Web Socket
        notificationWSService.notifyOnReaction(noteReactionNotificationDTO);

        return reactionDTO;
    }

    @DeleteMapping("/{reactionId}")
    public void delete(@PathVariable("currentUserId") int currentUserId,
                       @PathVariable("noteId") int noteId,
                       @PathVariable("reactionId") int reactionId) {

        User currentUser = userService.getById(currentUserId);
        Note note = noteService.getById(noteId);
        Reaction reaction = reactionService.getById(reactionId);

        reactionService.delete(currentUser, note, reaction);
    }
}
