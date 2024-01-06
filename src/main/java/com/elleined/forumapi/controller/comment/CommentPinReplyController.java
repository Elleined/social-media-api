package com.elleined.forumapi.controller.comment;

import com.elleined.forumapi.dto.CommentDTO;
import com.elleined.forumapi.dto.ReplyDTO;
import com.elleined.forumapi.mapper.CommentMapper;
import com.elleined.forumapi.mapper.ReplyMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.CommentService;
import com.elleined.forumapi.service.ReplyService;
import com.elleined.forumapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/posts/{postId}/comments")
public class CommentPinReplyController {
    private final UserService userService;

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    private final ReplyMapper replyMapper;
    private final ReplyService replyService;

    @GetMapping("/getPinnedReply/{commentId}")
    public ReplyDTO getPinnedReply(@PathVariable("commentId") int commentId) {
        Comment comment = commentService.getById(commentId);
        Reply pinnedReply = commentService.getPinnedReply(comment);

        return replyMapper.toDTO(pinnedReply);
    }

    @PatchMapping("/{commentId}/pinReply/{replyId}")
    public CommentDTO pinReply(@PathVariable("currentUserId") int currentUserId,
                               @PathVariable("commentId") int commentId,
                               @PathVariable("replyId") int replyId) {

        User currentUSer = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.getById(replyId);

        commentService.pin(currentUSer, comment, reply);
        return commentMapper.toDTO(comment);
    }
}
