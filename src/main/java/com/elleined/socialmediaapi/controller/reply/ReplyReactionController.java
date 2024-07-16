package com.elleined.socialmediaapi.controller.reply;

import com.elleined.socialmediaapi.dto.notification.reaction.ReplyReactionNotificationDTO;
import com.elleined.socialmediaapi.dto.reaction.ReactionDTO;
import com.elleined.socialmediaapi.mapper.notification.reaction.ReactionNotificationMapper;
import com.elleined.socialmediaapi.mapper.react.ReactionMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.notification.reaction.ReplyReactionNotification;
import com.elleined.socialmediaapi.model.reaction.Emoji;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.emoji.EmojiService;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.main.reply.ReplyService;
import com.elleined.socialmediaapi.service.notification.reaction.ReactionNotificationService;
import com.elleined.socialmediaapi.service.reaction.ReactionService;
import com.elleined.socialmediaapi.service.user.UserService;
import com.elleined.socialmediaapi.ws.notification.NotificationWSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/posts/{postId}/comments/{commentId}/replies/{replyId}/reactions")
public class ReplyReactionController {
    private final UserService userService;

    private final PostService postService;

    private final CommentService commentService;

    private final ReplyService replyService;

    private final ReactionService reactionService;
    private final ReactionMapper reactionMapper;

    private final EmojiService emojiService;

    private final ReactionNotificationService<ReplyReactionNotification, Reply> reactionNotificationService;
    private final ReactionNotificationMapper reactionNotificationMapper;

    private final NotificationWSService notificationWSService;

    @GetMapping
    public Page<ReactionDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                    @PathVariable("postId") int postId,
                                    @PathVariable("commentId") int commentId,
                                    @PathVariable("replyId") int replyId,
                                    @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                    @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                    @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                    @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.getById(replyId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return reactionService.getAll(currentUser, post, comment, reply, pageable)
                .map(reactionMapper::toDTO);
    }

    @GetMapping("/{emojiId}")
    public Page<ReactionDTO> getAllByEmoji(@PathVariable("currentUserId") int currentUserId,
                                                   @PathVariable("postId") int postId,
                                                   @PathVariable("commentId") int commentId,
                                                   @PathVariable("replyId") int replyId,
                                                   @RequestParam("emojiId") int emojiId,
                                           @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                           @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                           @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                           @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.getById(replyId);
        Emoji emoji = emojiService.getById(emojiId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return reactionService.getAllByEmoji(currentUser, post, comment, reply, emoji, pageable)
                .map(reactionMapper::toDTO);
    }

    @PostMapping
    public ReactionDTO save(@PathVariable("currentUserId") int currentUserId,
                            @PathVariable("postId") int postId,
                            @PathVariable("commentId") int commentId,
                            @PathVariable("replyId") int replyId,
                            @RequestParam("emojiId") int emojiId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.getById(replyId);
        Emoji emoji = emojiService.getById(emojiId);

        if (reactionService.isAlreadyReactedTo(currentUser, post, comment, reply)) {
            Reaction reaction = reactionService.getByUserReaction(currentUser, post, comment, reply);
            reactionService.update(currentUser, post, comment, reply, reaction, emoji);
            return reactionMapper.toDTO(reaction);
        }

        Reaction reaction = reactionService.save(currentUser, post, comment, reply, emoji);
        ReplyReactionNotification replyReactionNotification = reactionNotificationService.save(currentUser, reply, reaction);

        ReplyReactionNotificationDTO replyReactionNotificationDTO = reactionNotificationMapper.toDTO(replyReactionNotification);
        ReactionDTO reactionDTO = reactionMapper.toDTO(reaction);

        notificationWSService.notifyOnReaction(replyReactionNotificationDTO);

        return reactionDTO;
    }

    @DeleteMapping("/{reactionId}")
    public void delete(@PathVariable("currentUserId") int currentUserId,
                       @PathVariable("postId") int postId,
                       @PathVariable("commentId") int commentId,
                       @PathVariable("replyId") int replyId,
                       @PathVariable("reactionId") int reactionId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.getById(replyId);
        Reaction reaction = reactionService.getById(reactionId);

        reactionService.delete(currentUser, post, comment, reply, reaction);
    }
}
