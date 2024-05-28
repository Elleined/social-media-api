package com.elleined.socialmediaapi.controller.post;

import com.elleined.socialmediaapi.dto.hashtag.HashTagDTO;
import com.elleined.socialmediaapi.dto.main.PostDTO;
import com.elleined.socialmediaapi.mapper.hashtag.HashTagMapper;
import com.elleined.socialmediaapi.mapper.main.PostMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.service.hashtag.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts/hashtags")
public class PostHashTagController {
    private final HashTagService hashTagService;
    private final HashTagMapper hashTagMapper;

    private final PostMapper postMapper;

    @GetMapping
    public List<HashTagDTO> getAll(@RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                   @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                   @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                   @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);
        return hashTagService.getAll(pageable).stream()
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
    List<PostDTO> getAllByKeyword(@RequestParam("keyword") String keyword,
                                  @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                  @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                  @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                  @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);
        return hashTagService.getAllByKeyword(keyword, pageable).stream()
                .map(postMapper::toDTO)
                .toList();
    }
}
