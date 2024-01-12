package com.elleined.forumapi.controller.hashtag;

import com.elleined.forumapi.dto.HashTagDTO;
import com.elleined.forumapi.mapper.hashtag.HashTagMapper;
import com.elleined.forumapi.service.hashtag.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/posts/hashtags")
public class HashTagController {
    private final HashTagService hashTagService;
    private final HashTagMapper hashTagMapper;

    @GetMapping
    public Set<HashTagDTO> getAll() {
        return hashTagService.getAll().stream()
                .map(hashTagMapper::toDTO)
                .collect(Collectors.toSet());
    }

}
