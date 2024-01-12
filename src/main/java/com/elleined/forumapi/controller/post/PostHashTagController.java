package com.elleined.forumapi.controller.post;


import com.elleined.forumapi.dto.HashTagDTO;
import com.elleined.forumapi.dto.PostDTO;
import com.elleined.forumapi.mapper.PostMapper;
import com.elleined.forumapi.mapper.hashtag.HashTagMapper;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.hashtag.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/posts/hashtags")
public class PostHashTagController {
    private final UserService userService;

    private final HashTagService hashTagService;
    private final HashTagMapper hashTagMapper;

    private final PostMapper postMapper;

    @GetMapping
    public Set<HashTagDTO> getAll() {
        return hashTagService.getAll().stream()
                .map(hashTagMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @GetMapping("/keyword")
    public Set<HashTagDTO> searchHashTagByKeyword(@RequestParam("keyword") String keyword) {
        return hashTagService.searchHashTagByKeyword(keyword).stream()
                .map(hashTagMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @GetMapping("/search-posts-by-keyword")
    public Set<PostDTO> searchPostByHashtagKeyword(@PathVariable("currentUserId") int currentUserId,
                                                   @RequestParam("keyword") String keyword) {
        User currentUser = userService.getById(currentUserId);

        return hashTagService.getAllPostByHashTagKeyword(currentUser, keyword).stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toSet());
    }
}
