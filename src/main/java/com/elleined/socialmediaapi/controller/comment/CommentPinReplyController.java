package com.elleined.socialmediaapi.controller.comment;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.mapper.main.CommentMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.reply.ReplyService;
import com.elleined.socialmediaapi.service.pin.CommentPinReplyService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/posts/{postId}/comments/{commentId}/pin-reply")
public class CommentPinReplyController {
    private final UserService userService;

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final CommentPinReplyService commentPinReplyService;

    private final ReplyService replyService;

    @PatchMapping("/{replyId}")
    public CommentDTO pinReply(@RequestHeader("Authorization") String jwt,
                               @PathVariable("commentId") int commentId,
                               @PathVariable("replyId") int replyId) {

        User currentUser = userService.getByJWT(jwt);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.getById(replyId);

        commentPinReplyService.pin(currentUser, comment, reply);
        return commentMapper.toDTO(comment);
    }
}
