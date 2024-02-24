package com.elleined.socialmediaapi.service.hashtag;

import com.elleined.socialmediaapi.model.hashtag.HashTag;

import java.util.Set;

public interface HashTagService {
    Set<HashTag> getAll();

    boolean notExist(String name);

    HashTag getByKeyword(String keyword);

    HashTag save(HashTag hashTag);

//    Set<T> getAllByHashTagKeyword(User currentUser, String keyword);
//    HashTag save(T t, String keyword);
//    List<HashTag> saveAll(T t, Set<String> keywords);
}
