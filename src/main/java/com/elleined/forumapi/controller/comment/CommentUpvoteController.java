package com.elleined.forumapi.controller.comment;

import com.elleined.forumapi.dto.CommentDTO;
import com.elleined.forumapi.mapper.CommentMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.CommentService;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.upvote.CommentUpvoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/posts/{postId}/comments")
public class CommentUpvoteController {
    private final UserService userService;

    private final CommentService commentService;
    private final CommentUpvoteService commentUpvoteService;
    private final CommentMapper commentMapper;

    @PatchMapping("/{commentId}/upvote")
    public CommentDTO updateUpvote(@PathVariable("currentUserId") int currentUserId,
                                   @PathVariable("commentId") int commentId) {
        User respondent = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);

        Comment updatedComment = commentUpvoteService.upvote(respondent, comment);
        return commentMapper.toDTO(updatedComment);
    }
}
