package com.elleined.forumapi.controller.post;

import com.elleined.forumapi.dto.CommentDTO;
import com.elleined.forumapi.dto.PostDTO;
import com.elleined.forumapi.mapper.CommentMapper;
import com.elleined.forumapi.mapper.PostMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.CommentService;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.pin.PostPinCommentService;
import com.elleined.forumapi.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/posts/{postId}")
public class PostPinCommentController {
    private final UserService userService;

    private final PostService postService;
    private final PostMapper postMapper;
    private final PostPinCommentService postPinCommentService;

    private final CommentService commentService;
    private final CommentMapper commentMapper;


    @GetMapping("/pinned-comment")
    public CommentDTO getPinnedComment(@PathVariable("postId") int postId) {
        Post post = postService.getById(postId);
        Comment pinnedComment = postPinCommentService.getPinned(post);
        return commentMapper.toDTO(pinnedComment);
    }

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
