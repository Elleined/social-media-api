package com.elleined.socialmediaapi.service.hashtag;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.hashtag.HashTagMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.repository.hashtag.HashTagRepository;
import com.elleined.socialmediaapi.repository.main.PostRepository;
import com.elleined.socialmediaapi.validator.PageableUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class HashTagServiceImpl implements HashTagService {
    private final HashTagRepository hashTagRepository;
    private final HashTagMapper hashTagMapper;

    private final PostRepository postRepository;

    @Override
    public HashTag save(HashTag hashTag) {
        return hashTagRepository.save(hashTag);
    }

    @Override
    public HashTag getById(int id) throws ResourceNotFoundException {
        return hashTagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("HashTag with id of " + id + " doesn't exists!"));
    }

    @Override
    public Page<HashTag> getAll(Pageable pageable) {
        return hashTagRepository.findAll(pageable);
    }

    @Override
    public boolean isExists(String keyword) {
        return hashTagRepository.findAll().stream()
                .map(HashTag::getKeyword)
                .anyMatch(keyword::equalsIgnoreCase);
    }

    @Override
    public HashTag getByKeyword(String keyword) {
        return hashTagRepository.findAll().stream()
                .filter(hashTag -> hashTag.getKeyword().equalsIgnoreCase(keyword))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Hashtag with keyword of " + keyword + " doesn't exists!"));
    }

    @Override
    public Page<Post> getAllByKeyword(String keyword, Pageable pageable) {
        List<Post> posts = postRepository.findAllByKeyword(keyword, pageable)
                .filter(Post::isActive)
                .toList();

        return PageableUtil.toPage(posts, pageable);
    }

    @Override
    public HashTag save(String keyword) {
        if (isExists(keyword))
            return getByKeyword(keyword);

        HashTag hashTag = hashTagMapper.toEntity(keyword);
        hashTagRepository.save(hashTag);
        log.debug("Saving hashtag success!");
        return hashTag;
    }
}
