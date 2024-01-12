package com.elleined.forumapi.service.hashtag;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.hashtag.HashTag;

import java.util.List;
import java.util.Set;

public interface HashTagService {
    Set<HashTag> getAll();

    boolean notExist(String name);

    HashTag getByKeyword(String keyword);

//    Set<T> getAllByHashTagKeyword(User currentUser, String keyword);
//    HashTag save(T t, String keyword);
//    List<HashTag> saveAll(T t, Set<String> keywords);
}
