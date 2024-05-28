package com.elleined.socialmediaapi.controller.post;

import com.elleined.socialmediaapi.dto.main.PostDTO;
import com.elleined.socialmediaapi.mapper.main.PostMapper;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{currentUserId}/shared-posts")
public class SharedPostController {
    private final UserService userService;
    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping
    public List<PostDTO> getAllSharedPost(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return postService.getAllSharedPosts(currentUser, ).stream()
                .map(postMapper::toDTO)
                .toList();
    }

    @PostMapping("/{postId}")
    public PostDTO sharePost(@PathVariable("currentUserId") int currentUserId,
                             @PathVariable("postId") int postId) {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        Post sharedPost = postService.sharePost(currentUser, post);
        return postMapper.toDTO(sharedPost);
    }

    @DeleteMapping("/{postId}")
    public void unSharePost(@PathVariable("currentUserId") int currentUserId,
                            @PathVariable("postId") int postId) {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        postService.unSharePost(currentUser, post);
    }
}
