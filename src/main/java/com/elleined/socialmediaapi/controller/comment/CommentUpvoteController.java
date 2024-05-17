package com.elleined.socialmediaapi.controller.comment;

import com.elleined.socialmediaapi.dto.CommentDTO;
import com.elleined.socialmediaapi.mapper.CommentMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.comment.vote.CommentVoteService;
import com.elleined.socialmediaapi.service.user.UserService;
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
    private final CommentVoteService commentUpvoteService;
    private final CommentMapper commentMapper;

    @PatchMapping("/{commentId}/upvote")
    public CommentDTO updateUpvote(@PathVariable("currentUserId") int currentUserId,
                                   @PathVariable("commentId") int commentId) {
        User respondent = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);

        Comment updatedComment = commentUpvoteService.upVote(respondent, comment);
        return commentMapper.toDTO(updatedComment);
    }
}
