package com.elleined.forumapi.service.hashtag;

import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.hashtag.HashTag;

import java.util.List;
import java.util.Set;

public interface HashTagService {
    Set<Post> searchPostByHashtagKeyword(String keyword);
    List<HashTag> save(Post post, List<String> keywords);
}
