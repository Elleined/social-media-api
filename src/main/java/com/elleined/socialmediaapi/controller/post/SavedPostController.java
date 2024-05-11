package com.elleined.socialmediaapi.controller.post;

import com.elleined.socialmediaapi.dto.PostDTO;
import com.elleined.socialmediaapi.mapper.PostMapper;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.service.UserService;
import com.elleined.socialmediaapi.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{currentUserId}/posts/{postId}/saved-posts")
public class SavedPostController {
    private final UserService userService;

    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping
    public Set<PostDTO> getAllSavedPost(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return postService.getAllSavedPosts(currentUser).stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @PostMapping
    public PostDTO savedPost(@PathVariable("currentUserId") int currentUserId,
                             @PathVariable("postId") int postId) {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        postService.savedPost(currentUser, post);
        return postMapper.toDTO(post);
    }

    @DeleteMapping
    public void unSavedPost(@PathVariable("currentUserId") int currentUserId,
                            @PathVariable("postId") int postId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        postService.unSavedPost(currentUser, post);
    }
}
