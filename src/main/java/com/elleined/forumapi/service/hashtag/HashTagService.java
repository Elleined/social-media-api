package com.elleined.forumapi.service.hashtag;

import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.hashtag.HashTag;

import java.util.List;

// String will be replace by entity
public interface HashTagService {
    List<HashTag> getAll(Post post);
    List<HashTag> getAllByKeyword(String keyword);
    List<Post> searchByKeyword(String keyword);
    HashTag save(Post post, String keyword);
    List<HashTag> save(Post post, List<String> word);
}
