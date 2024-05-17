package com.elleined.socialmediaapi.service.hashtag;

import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.exception.resource.ResourceAlreadyExistsException;
import com.elleined.socialmediaapi.mapper.hashtag.HashTagMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.repository.hashtag.HashTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HashTagServiceImpl implements HashTagService {
    private final HashTagRepository hashTagRepository;
    private final HashTagMapper hashTagMapper;

    @Override
    public Set<HashTag> getAll() {
        return new HashSet<>(hashTagRepository.findAll());
    }

    @Override
    public boolean isExists(String keyword) {
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
    public HashTag save(String keyword) {
        if (isExists(keyword)) throw new ResourceAlreadyExistsException("Hashtag with keyword of " + keyword + " already exists!");
        HashTag hashTag = hashTagMapper.toEntity(keyword);
        hashTagRepository.save(hashTag);
        log.debug("Saving hashtag success!");
        return hashTag;
    }
}
