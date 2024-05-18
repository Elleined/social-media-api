package com.elleined.socialmediaapi.service.hashtag;

import com.elleined.socialmediaapi.exception.resource.ResourceAlreadyExistsException;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.service.CustomService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface HashTagService extends CustomService<HashTag> {
    HashTag save(String keyword);
    boolean isExists(String keyword);

    default Set<HashTag> saveAll(Set<String> keywords) {
        if (keywords.stream().anyMatch(this::isExists)) throw new ResourceAlreadyExistsException("Cannot save all! Bacause one of hashtag keyword already exists!");
        return keywords.stream()
                .map(this::save)
                .collect(Collectors.toSet());
    }
}
