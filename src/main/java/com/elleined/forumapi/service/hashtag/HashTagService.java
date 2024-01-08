package com.elleined.forumapi.service.hashtag;

import java.util.List;

// String will be replace by entity
public interface HashTagService {
    List<String> getAll();

    List<String> searchHashTag(String keyword);

}
