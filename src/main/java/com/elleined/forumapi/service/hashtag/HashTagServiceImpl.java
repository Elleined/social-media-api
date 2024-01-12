package com.elleined.forumapi.service.hashtag;

import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.model.hashtag.HashTag;
import com.elleined.forumapi.repository.HashTagRepository;
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

    @Override
    public Set<HashTag> getAll() {
        return new HashSet<>(hashTagRepository.findAll());
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
}
