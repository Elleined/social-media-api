package com.elleined.forumapi.service.hashtag;

import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.mapper.hashtag.HashTagMapper;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.hashtag.HashTag;
import com.elleined.forumapi.repository.HashTagRepository;
import com.elleined.forumapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HashTagServiceImpl implements HashTagService {
    private final HashTagRepository hashTagRepository;
    private final HashTagMapper hashtagMapper;

    private final PostRepository postRepository;
    @Override
    public Set<HashTag> getAll() {
        return new HashSet<>(hashTagRepository.findAll());
    }


    @Override
    public Set<Post> getAllPostByHashTagKeyword(User currentUser, String keyword) {
        return hashTagRepository.getAllPostByHashTagKeyword(keyword).stream()
                .filter(post -> !post.getAuthor().equals(currentUser))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean notExist(String keyword) {
        return hashTagRepository.findAll().stream()
                .map(HashTag::getKeyword)
                .noneMatch(keyword::equalsIgnoreCase);
    }


    @Override
    public HashTag getByKeyword(String keyword) {
        return hashTagRepository.findAll().stream()
                .filter(hashTag -> hashTag.getKeyword().equalsIgnoreCase(keyword))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Hashtag with keyword of " + keyword + " does not exists!"));
    }

    @Override
    public HashTag save(Post post, String keyword) {
        if (this.notExist(keyword)) {
            HashTag hashTag = hashtagMapper.toEntity(keyword);

            post.getHashTags().add(hashTag);
            hashTag.getPosts().add(post);

            postRepository.save(post);
            hashTagRepository.save(hashTag);
            log.debug("HashTag with keyword of {} saved successfully", keyword);
            return hashTag;
        }

        HashTag hashTag = this.getByKeyword(keyword);

        post.getHashTags().add(hashTag);
        hashTag.getPosts().add(post);

        postRepository.save(post);
        hashTagRepository.save(hashTag);
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
