package com.elleined.socialmediaapi.service.hashtag.entity;

import com.elleined.socialmediaapi.mapper.hashtag.HashTagMapper;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.repository.hashtag.HashTagRepository;
import com.elleined.socialmediaapi.repository.main.PostRepository;
import com.elleined.socialmediaapi.service.hashtag.HashTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostHashTagService implements EntityHashTagService<Post> {
    private final HashTagRepository hashTagRepository;

    private final HashTagService hashTagService;
    private final HashTagMapper hashTagMapper;

    private final PostRepository postRepository;

    @Override
    public Set<Post> getAllByHashTagKeyword(User currentUser, String keyword) {
        return hashTagRepository.getAllPostByHashTagKeyword(keyword).stream()
                .filter(post -> !post.getAuthor().equals(currentUser))
                .collect(Collectors.toSet());
    }


    @Override
    public HashTag save(Post post, String keyword) {
        if (hashTagService.notExist(keyword)) {
            HashTag hashTag = hashTagMapper.toEntity(keyword);

            post.getHashTags().add(hashTag);
            hashTag.getPosts().add(post);

            hashTagService.save(hashTag);
            postRepository.save(post);
            log.debug("HashTag with keyword of {} saved successfully", keyword);
            return hashTag;
        }

        HashTag hashTag = hashTagService.getByKeyword(keyword);

        post.getHashTags().add(hashTag);
        hashTag.getPosts().add(post);

        hashTagService.save(hashTag);
        postRepository.save(post);
        log.debug("HashTag with keyword of {} saved successfully", keyword);
        return hashTag;
    }

    @Override
    public List<HashTag> saveAll(Post post, Set<String> keywords) {
        return keywords.stream()
                .map(keyword -> this.save(post, keyword))
                .toList();
    }
}
