package com.elleined.forumapi.service.hashtag;

import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.hashtag.HashTag;

import java.util.List;
import java.util.Set;

public interface HashTagService {
    Set<Post> searchPostByHashtagKeyword(User currentUser, String keyword);
    List<HashTag> saveAll(Post post, Set<String> keywords);
}
