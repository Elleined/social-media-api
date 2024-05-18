package com.elleined.socialmediaapi.controller.comment;

import com.elleined.socialmediaapi.dto.react.ReactDTO;
import com.elleined.socialmediaapi.mapper.react.ReactionMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.React;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.emoji.EmojiService;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
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
@RequestMapping("/users/{currentUserId}/posts/{postId}/comments/{commentId}/reactions")
public class CommentReactionController {
    private final UserService userService;

    private final PostService postService;

    private final CommentService commentService;

    private final ReactionService reactionService;
    private final ReactionMapper reactionMapper;

    private final EmojiService emojiService;

    @GetMapping
    public List<ReactDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                 @PathVariable("postId") int postId,
                                 @PathVariable("commentId") int commentId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        return reactionService.getAll(currentUser, post, comment).stream()
                .map(reactionMapper::toDTO)
                .toList();
    }

    @GetMapping("/emoji")
    public List<ReactDTO> getAllByEmoji(@PathVariable("currentUserId") int currentUserId,
                                        @PathVariable("postId") int postId,
                                        @PathVariable("commentId") int commentId,
                                        @RequestParam("emojiId") int emojiId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);
        Emoji emoji = emojiService.getById(emojiId);

        return reactionService.getAllByEmoji(currentUser, post, comment, emoji).stream()
                .map(reactionMapper::toDTO)
                .toList();
    }

    @PostMapping
    public ReactDTO save(@PathVariable("currentUserId") int currentUserId,
                         @PathVariable("postId") int postId,
                         @PathVariable("commentId") int commentId,
                         @RequestParam("emojiId") int emojiId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);
        Emoji emoji = emojiService.getById(emojiId);

        if (reactionService.isAlreadyReactedTo(currentUser, post, comment)) {
            React react = reactionService.getByUserReaction(currentUser, post, comment);
            reactionService.update(currentUser, post, comment, react, emoji);
            return reactionMapper.toDTO(react);
        }

        React react = reactionService.save(currentUser, post, comment, emoji);
        return reactionMapper.toDTO(react);
    }

    @DeleteMapping("/{reactionId}")
    public void delete(@PathVariable("currentUserId") int currentUserId,
                       @PathVariable("postId") int postId,
                       @PathVariable("commentId") int commentId,
                       @PathVariable("reactionId") int reactionId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);
        React react = reactionService.getById(reactionId);

        reactionService.delete(currentUser, post, comment, react);
    }
}
