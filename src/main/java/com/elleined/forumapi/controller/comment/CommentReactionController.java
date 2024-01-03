package com.elleined.forumapi.controller.comment;

import com.elleined.forumapi.dto.ReactionDTO;
import com.elleined.forumapi.mapper.react.CommentReactionMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.emoji.Emoji;
import com.elleined.forumapi.model.react.CommentReact;
import com.elleined.forumapi.service.CommentService;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.emoji.EmojiService;
import com.elleined.forumapi.service.react.ReactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/comments/{commentId}/reactions")
public class CommentReactionController {
    private final UserService userService;

    private final CommentService commentService;
    private final ReactionService<Comment, CommentReact> commentReactionService;
    private final CommentReactionMapper commentReactionMapper;

    private final EmojiService emojiService;

    @GetMapping
    public List<ReactionDTO> getAll(@PathVariable("commentId") int commentId) {
        Comment comment = commentService.getById(commentId);
        return commentReactionService.getAll(comment).stream()
                .map(commentReactionMapper::toDTO)
                .toList();
    }

    @GetMapping("/type")
    public List<ReactionDTO> getAllReactionByEmojiType(@PathVariable("commentId") int commentId,
                                                       @RequestParam("type") Emoji.Type type) {
        Comment comment = commentService.getById(commentId);
        return commentReactionService.getAllReactionByEmojiType(comment, type).stream()
                .map(commentReactionMapper::toDTO)
                .toList();
    }

    @PostMapping
    public ReactionDTO save(@PathVariable("currentUserId") int currentUserId,
                            @PathVariable("commentId") int commentId,
                            @RequestParam("type") Emoji.Type type) {
        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);
        Emoji emoji = emojiService.getByType(type);

        if (currentUser.isAlreadyReactedTo(comment)) {
            CommentReact commentReact = commentReactionService.getByUserReaction(currentUser, comment);
            commentReactionService.update(currentUser, comment, commentReact, emoji);
            return commentReactionMapper.toDTO(commentReact);
        }
        CommentReact commentReact = commentReactionService.save(currentUser, comment, emoji);
        return commentReactionMapper.toDTO(commentReact);
    }

    @DeleteMapping("/{reactionId}")
    public void delete(@PathVariable("currentUserId") int currentUserId,
                       @PathVariable("commentId") int commentId,
                       @PathVariable("reactionId") int reactionId) {
        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);
        CommentReact commentReact = commentReactionService.getById(reactionId);
        commentReactionService.delete(currentUser, comment, commentReact);
    }
}
