package com.elleined.socialmediaapi.service.hashtag;

import com.elleined.socialmediaapi.exception.resource.ResourceAlreadyExistsException;
import com.elleined.socialmediaapi.model.hashtag.HashTag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface HashTagService {
    HashTag getByKeyword(String keyword);
    Set<HashTag> getAll();
    HashTag save(String keyword);
    boolean isExists(String keyword);
    List<HashTag> getAllById(Set<Integer> ids);

    default Set<HashTag> saveAll(Set<String> keywords) {
        if (keywords.stream().anyMatch(this::isExists)) throw new ResourceAlreadyExistsException("Cannot save all! Bacause one of hashtag keyword already exists!");
        return keywords.stream()
                .map(this::save)
                .collect(Collectors.toSet());
    }
}
