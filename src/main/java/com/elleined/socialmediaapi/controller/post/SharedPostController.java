package com.elleined.socialmediaapi.controller.post;

import com.elleined.socialmediaapi.dto.PostDTO;
import com.elleined.socialmediaapi.mapper.PostMapper;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.UserService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{currentUserId}/posts/{postId}/shared-posts")
public class SharedPostController {
    private final UserService userService;
    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping
    public Set<PostDTO> getAllSharedPost(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return postService.getAllSharedPosts(currentUser).stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @PostMapping
    public PostDTO sharePost(@PathVariable("currentUserId") int currentUserId,
                             @PathVariable("postId") int postId) {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        Post sharedPost = postService.sharePost(currentUser, post);
        return postMapper.toDTO(sharedPost);
    }

    @DeleteMapping
    public ResponseEntity<PostDTO> unSharePost(@PathVariable("currentUserId") int currentUserId,
                                               @PathVariable("postId") int postId) {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        postService.unSharePost(currentUser, post);
        return ResponseEntity.noContent().build();
    }
}
