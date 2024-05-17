package com.elleined.socialmediaapi.controller.post;

import com.elleined.socialmediaapi.dto.ReactionDTO;
import com.elleined.socialmediaapi.mapper.react.PostReactionMapper;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.service.UserService;
import com.elleined.socialmediaapi.service.emoji.EmojiService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.react.ReactionService;
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
    private final ReactionService<Post, PostReact> postReactionService;
    private final PostReactionMapper postReactionMapper;

    private final EmojiService emojiService;
    @GetMapping
    public List<ReactionDTO> getAll(@PathVariable("postId") int postId) {
        Post post = postService.getById(postId);
        return postReactionService.getAll(post).stream()
                .map(postReactionMapper::toDTO)
                .toList();
    }

    @GetMapping("/type")
    public List<ReactionDTO> getAllReactionByEmojiType(@PathVariable("postId") int postId,
                                                       @RequestParam("type") Emoji.Type type) {
        Post post = postService.getById(postId);
        return postReactionService.getAllReactionByEmojiType(post, type).stream()
                .map(postReactionMapper::toDTO)
                .toList();
    }

    @PostMapping
    public ReactionDTO save(@PathVariable("currentUserId") int currentUserId,
                          @PathVariable("postId") int postId,
                          @RequestParam("type") Emoji.Type type) {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Emoji emoji = emojiService.getByType(type);

        if (currentUser.isAlreadyReactedTo(post)) {
            PostReact postReact = postReactionService.getByUserReaction(currentUser, post);
            postReactionService.update(currentUser, post, postReact, emoji);
            return postReactionMapper.toDTO(postReact);
        }
        PostReact postReact = postReactionService.save(currentUser, post, emoji);
        return postReactionMapper.toDTO(postReact);
    }

    @DeleteMapping("/{reactionId}")
    public void delete(@PathVariable("currentUserId") int currentUserId,
                       @PathVariable("postId") int postId,
                       @PathVariable("reactionId") int reactionId) {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        PostReact postReact = postReactionService.getById(reactionId);
        postReactionService.delete(currentUser, postReact);
    }
}
