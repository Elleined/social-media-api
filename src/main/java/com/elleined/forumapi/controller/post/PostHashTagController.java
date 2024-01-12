package com.elleined.forumapi.controller.post;


import com.elleined.forumapi.dto.PostDTO;
import com.elleined.forumapi.mapper.PostMapper;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.hashtag.entity.EntityHashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/posts/hashtags")
public class PostHashTagController {
    private final UserService userService;

    private final EntityHashTagService<Post> postHashTagService;

    private final PostMapper postMapper;


    @GetMapping("/search-posts-by-keyword")
    public Set<PostDTO> searchPostByHashtagKeyword(@PathVariable("currentUserId") int currentUserId,
                                                   @RequestParam("keyword") String keyword) {
        User currentUser = userService.getById(currentUserId);

        return postHashTagService.getAllByHashTagKeyword(currentUser, keyword).stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toSet());
    }
}
