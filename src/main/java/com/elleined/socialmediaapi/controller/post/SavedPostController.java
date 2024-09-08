package com.elleined.socialmediaapi.controller.post;

import com.elleined.socialmediaapi.dto.main.PostDTO;
import com.elleined.socialmediaapi.mapper.main.PostMapper;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/saved-posts")
public class SavedPostController {
    private final UserService userService;

    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping
    public Page<PostDTO> getAllSavedPost(@RequestHeader("Authorization") String jwt,
                                         @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                         @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                         @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                         @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return postService.getAllSavedPosts(currentUser, pageable)
                .map(postMapper::toDTO);
    }

    @PostMapping("/{postId}")
    public PostDTO savedPost(@RequestHeader("Authorization") String jwt,
                             @PathVariable("postId") int postId) {
        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);
        postService.savedPost(currentUser, post);
        return postMapper.toDTO(post);
    }

    @DeleteMapping("/{postId}")
    public void unSavedPost(@RequestHeader("Authorization") String jwt,
                            @PathVariable("postId") int postId) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);
        postService.unSavedPost(currentUser, post);
    }
}
