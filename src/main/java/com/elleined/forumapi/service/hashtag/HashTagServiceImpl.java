package com.elleined.forumapi.service.hashtag;

import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.hashtag.HashTag;
import com.elleined.forumapi.repository.HashTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HashTagServiceImpl implements HashTagService {
    private final HashTagRepository hashTagRepository;

    @Override
    public List<HashTag> getAllByKeyword(String keyword) {
        return null;
    }

    @Override
    public Set<Post> searchByKeyword(String keyword) {
        return null;
    }

    @Override
    public HashTag save(Post post, String keyword) {
        return null;
    }

    @Override
    public List<HashTag> save(Post post, List<String> word) {
        return null;
    }
}
