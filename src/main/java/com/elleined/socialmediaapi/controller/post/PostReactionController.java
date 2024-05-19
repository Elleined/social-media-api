package com.elleined.socialmediaapi.controller.post;

import com.elleined.socialmediaapi.dto.reaction.ReactionDTO;
import com.elleined.socialmediaapi.mapper.react.ReactionMapper;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.emoji.EmojiService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.react.ReactionService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/posts/{postId}/reactions")
public class PostReactionController {
    private final UserService userService;

    private final PostService postService;

    private final ReactionService reactionService;
    private final ReactionMapper reactionMapper;

    private final EmojiService emojiService;

    @GetMapping
    public List<ReactionDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                    @PathVariable("postId") int postId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        return reactionService.getAll(currentUser, post).stream()
                .map(reactionMapper::toDTO)
                .toList();
    }

    @GetMapping("/emoji")
    public List<ReactionDTO> getAllByEmoji(@PathVariable("currentUserId") int currentUserId,
                                                       @PathVariable("postId") int postId,
                                                       @RequestParam("emojiId") int emojiId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Emoji emoji = emojiService.getById(emojiId);

        return reactionService.getAllByEmoji(currentUser, post, emoji).stream()
                .map(reactionMapper::toDTO)
                .toList();
    }

    @PostMapping
    public ReactionDTO save(@PathVariable("currentUserId") int currentUserId,
                          @PathVariable("postId") int postId,
                            @RequestParam("emojiId") int emojiId) {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Emoji emoji = emojiService.getById(emojiId);

        if (reactionService.isAlreadyReactedTo(currentUser, post)) {
            Reaction reaction = reactionService.getByUserReaction(currentUser, post);
            reactionService.update(currentUser, post, reaction, emoji);
            return reactionMapper.toDTO(reaction);
        }

        Reaction reaction = reactionService.save(currentUser, post, emoji);
        return reactionMapper.toDTO(reaction);
    }

    @DeleteMapping("/{reactionId}")
    public void delete(@PathVariable("currentUserId") int currentUserId,
                       @PathVariable("postId") int postId,
                       @PathVariable("reactionId") int reactionId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Reaction reaction = reactionService.getById(reactionId);
        reactionService.delete(currentUser, post, reaction);
    }
}
