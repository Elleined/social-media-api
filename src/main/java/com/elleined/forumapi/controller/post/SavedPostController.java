package com.elleined.forumapi.controller.post;

import com.elleined.forumapi.dto.PostDTO;
import com.elleined.forumapi.mapper.PostMapper;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{currentUserId}/posts/saved-posts")
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

    @PostMapping("/{postId}")
    public PostDTO savedPost(@PathVariable("currentUserId") int currentUserId,
                             @PathVariable("postId") int postId) {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        postService.savedPost(currentUser, post);
        return postMapper.toDTO(post);
    }

    @DeleteMapping("/{postId}")
    public void unSavedPost(@PathVariable("currentUserId") int currentUserId,
                            @PathVariable("postId") int postId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        postService.unSavedPost(currentUser, post);
    }
}
