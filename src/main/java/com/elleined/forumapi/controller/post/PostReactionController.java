package com.elleined.forumapi.controller.post;

import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.emoji.Emoji;
import com.elleined.forumapi.model.react.PostReact;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.emoji.EmojiService;
import com.elleined.forumapi.service.post.PostService;
import com.elleined.forumapi.service.react.ReactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/posts/{postId}/reactions")
public class PostReactionController {
    private final UserService userService;
    private final PostService postService;
    private final ReactionService<Post, PostReact> postReactionService;

    private final EmojiService emojiService;
    @GetMapping
    public List<PostReact> getAll(@PathVariable("postId") int postId) {
        Post post = postService.getById(postId);
        return postReactionService.getAll(post);
    }

    @GetMapping("/type")
    public List<PostReact> getAllReactionByEmojiType(@PathVariable("postId") int postId,
                                                     @RequestParam("emojiType") Emoji.Type emojiType) {
        Post post = postService.getById(postId);
        return postReactionService.getAllReactionByEmojiType(post, emojiType);
    }

    @PostMapping
    public PostReact save(@PathVariable("currentUserId") int currentUserId,
                          Post post, Emoji emoji) {
        return null;
    }

    @PatchMapping("/{reactionId}/replace-emoji/{emojiId}")
    public PostReact update(@PathVariable("currentUserId") int currentUserId,
                            @PathVariable("postId") int postId,
                            @PathVariable("reactionId") int reactionId,
                            @RequestParam("emojiId") int emojiId) {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        PostReact postReact = postReactionService.getById(reactionId);
        Emoji emoji = emojiService.getById(emojiId);
        return postReactionService.update(currentUser, post, postReact, emoji);
    }
}
