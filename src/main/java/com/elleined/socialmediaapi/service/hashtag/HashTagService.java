package com.elleined.socialmediaapi.service.hashtag;

import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.CustomService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface HashTagService extends CustomService<HashTag> {
    HashTag save(String keyword);
    boolean isExists(String keyword);
    HashTag getByKeyword(String keyword);

    List<Post> getAllByKeyword(String keyword);

    default Set<HashTag> saveAll(Set<String> keywords) {
        if (keywords.isEmpty()) return null;
        return keywords.stream()
                .map(this::save)
                .collect(Collectors.toSet());
    }
}
