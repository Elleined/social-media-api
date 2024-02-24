package com.elleined.socialmediaapi.service.hashtag.entity;

import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.model.hashtag.HashTag;

import java.util.List;
import java.util.Set;

public interface EntityHashTagService<T> {
    Set<T> getAllByHashTagKeyword(User currentUser, String keyword);
    HashTag save(T t, String keyword);
    List<HashTag> saveAll(T t, Set<String> keywords);
}
