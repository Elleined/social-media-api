package com.elleined.socialmediaapi.controller.post;


import com.elleined.socialmediaapi.dto.PostDTO;
import com.elleined.socialmediaapi.mapper.PostMapper;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.UserService;
import com.elleined.socialmediaapi.service.hashtag.entity.EntityHashTagService;
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
