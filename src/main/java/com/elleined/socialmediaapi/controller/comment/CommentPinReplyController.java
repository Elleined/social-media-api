package com.elleined.socialmediaapi.controller.comment;

import com.elleined.socialmediaapi.dto.CommentDTO;
import com.elleined.socialmediaapi.dto.ReplyDTO;
import com.elleined.socialmediaapi.mapper.CommentMapper;
import com.elleined.socialmediaapi.mapper.ReplyMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.user.UserService;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.reply.ReplyService;
import com.elleined.socialmediaapi.service.pin.CommentPinReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/posts/{postId}/comments")
public class CommentPinReplyController {
    private final UserService userService;

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final CommentPinReplyService commentPinReplyService;

    private final ReplyMapper replyMapper;
    private final ReplyService replyService;

    @GetMapping("/{commentId}/pinned-reply")
    public ReplyDTO getPinnedReply(@PathVariable("commentId") int commentId) {
        Comment comment = commentService.getById(commentId);
        Reply pinnedReply = commentPinReplyService.getPinned(comment);

        return replyMapper.toDTO(pinnedReply);
    }

    @PatchMapping("/{commentId}/pin-reply/{replyId}")
    public CommentDTO pinReply(@PathVariable("currentUserId") int currentUserId,
                               @PathVariable("commentId") int commentId,
                               @PathVariable("replyId") int replyId) {

        User currentUSer = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.getById(replyId);

        commentPinReplyService.pin(currentUSer, comment, reply);
        return commentMapper.toDTO(comment);
    }
}
