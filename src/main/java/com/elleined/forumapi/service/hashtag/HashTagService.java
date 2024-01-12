package com.elleined.forumapi.service.hashtag;

import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.hashtag.HashTag;

import java.util.List;
import java.util.Set;

public interface HashTagService {
    Set<Post> searchByKeyword(String keyword);
    HashTag save(Post post, String keyword);
    List<HashTag> save(Post post, List<String> word);
}
