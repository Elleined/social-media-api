package com.elleined.forumapi.controller.comment;

import com.elleined.forumapi.dto.CommentDTO;
import com.elleined.forumapi.mapper.CommentMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.CommentLike;
import com.elleined.forumapi.service.CommentService;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.ws.notification.like.CommentWSLikeNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/posts/{postId}/comments")
public class CommentLikeController {
    private final UserService userService;

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    private final CommentWSLikeNotificationService commentLikeWSNotificationService;

    @PatchMapping("/like/{commentId}")
    public CommentDTO like(@PathVariable("currentUserId") int respondentId,
                           @PathVariable("commentId") int commentId) {

        User respondent = userService.getById(respondentId);
        Comment comment = commentService.getById(commentId);

        if (commentService.isLiked(respondent, comment)) {
            commentService.unLike(respondent, comment);
            return commentMapper.toDTO(comment);
        }

        CommentLike commentLike = commentService.like(respondent, comment);
        commentLikeWSNotificationService.broadcast(commentLike);

        return commentMapper.toDTO(comment);
    }
}
