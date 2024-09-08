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
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/posts/{postId}/pin-comment")
public class PostPinCommentController {
    private final UserService userService;

    private final PostService postService;
    private final PostMapper postMapper;
    private final PostPinCommentService postPinCommentService;

    private final CommentService commentService;

    @PatchMapping("/{commentId}")
    public PostDTO pinComment(@RequestHeader("Authorization") String jwt,
                              @PathVariable("postId") int postId,
                              @PathVariable("commentId") int commentId) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        postPinCommentService.pin(currentUser, post, comment);
        return postMapper.toDTO(post);
    }
}
