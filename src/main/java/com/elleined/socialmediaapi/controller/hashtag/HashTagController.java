package com.elleined.socialmediaapi.controller.hashtag;

import com.elleined.socialmediaapi.dto.hashtag.HashTagDTO;
import com.elleined.socialmediaapi.dto.main.PostDTO;
import com.elleined.socialmediaapi.mapper.hashtag.HashTagMapper;
import com.elleined.socialmediaapi.mapper.main.PostMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.service.hashtag.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/forum/posts/hashtags")
public class HashTagController {
    private final HashTagService hashTagService;
    private final HashTagMapper hashTagMapper;

    private final PostMapper postMapper;

    @GetMapping
    public List<HashTagDTO> getAll() {
        return hashTagService.getAll().stream()
                .map(hashTagMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public HashTagDTO getById(@PathVariable("id") int id) {
        HashTag hashTag = hashTagService.getById(id);
        return hashTagMapper.toDTO(hashTag);
    }

    @GetMapping("/get-all-by-id")
    public List<HashTagDTO> getAllById(@RequestBody List<Integer> ids) {
        return hashTagService.getAllById(ids).stream()
                .map(hashTagMapper::toDTO)
                .toList();
    }


    @GetMapping("/keyword")
    List<PostDTO> getAllByKeyword(@RequestParam("keyword") String keyword) {
        return hashTagService.getAllByKeyword(keyword).stream()
                .map(postMapper::toDTO)
                .toList();
    }
}
