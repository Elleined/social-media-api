package com.elleined.socialmediaapi.service.hashtag;

import com.elleined.socialmediaapi.exception.exception.resource.ResourceAlreadyExistsException;
import com.elleined.socialmediaapi.model.hashtag.HashTag;

import java.util.List;
import java.util.Set;

public interface HashTagService {
    HashTag getByKeyword(String keyword);
    Set<HashTag> getAll();
    HashTag save(String keyword);
    boolean isExists(String keyword);

    default List<HashTag> saveAll(List<String> keywords) {
        if (keywords.stream().anyMatch(this::isExists)) throw new ResourceAlreadyExistsException("Cannot save all! Bacause one of hashtag keyword already exists!");
        return keywords.stream()
                .map(this::save)
                .toList();
    }
}
