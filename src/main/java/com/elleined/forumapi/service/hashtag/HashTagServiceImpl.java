package com.elleined.forumapi.service.hashtag;

import com.elleined.forumapi.mapper.hashtag.HashtagMapper;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.hashtag.HashTag;
import com.elleined.forumapi.repository.HashTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HashTagServiceImpl implements HashTagService {
    private final HashTagRepository hashTagRepository;
    private final HashtagMapper hashtagMapper;

    @Override
    public Set<Post> searchPostByHashtagKeyword(String keyword) {
        return hashTagRepository.searchPostByHashtagKeyword(keyword);
    }

    @Override
    public List<HashTag> save(Post post, List<String> keywords) {
        List<HashTag> hashTags = keywords.stream()
                .map(keyword -> hashtagMapper.toEntity(keyword, post))
                .toList();
        hashTagRepository.saveAll(hashTags);
        log.debug("Hashtags with keywords of {} saved successfully!", keywords);
        return hashTags;
    }
}
