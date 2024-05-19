package com.elleined.socialmediaapi.controller.post;

import com.elleined.socialmediaapi.dto.main.PostDTO;
import com.elleined.socialmediaapi.mapper.main.PostMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.pin.PostPinCommentService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/posts/{postId}")
public class PostPinCommentController {
    private final UserService userService;

    private final PostService postService;
    private final PostMapper postMapper;
    private final PostPinCommentService postPinCommentService;

    private final CommentService commentService;

    @PatchMapping("/pin-comment/{commentId}")
    public PostDTO pinComment(@PathVariable("currentUserId") int currentUserId,
                              @PathVariable("postId") int postId,
                              @PathVariable("commentId") int commentId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        postPinCommentService.pin(currentUser, post, comment);
        return postMapper.toDTO(post);
    }
}
