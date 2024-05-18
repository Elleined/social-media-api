package com.elleined.socialmediaapi.service.hashtag;

import com.elleined.socialmediaapi.exception.resource.ResourceAlreadyExistsException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.hashtag.HashTagMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.repository.hashtag.HashTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HashTagServiceImpl implements HashTagService {
    private final HashTagRepository hashTagRepository;
    private final HashTagMapper hashTagMapper;

    @Override
    public HashTag save(HashTag hashTag) {
        return hashTagRepository.save(hashTag);
    }

    @Override
    public HashTag getById(int id) throws ResourceNotFoundException {
        return hashTagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("HashTag with id of " + id + " doesn't exists!"));
    }

    @Override
    public List<HashTag> getAll() {
        return hashTagRepository.findAll().stream()
                .sorted(Comparator.comparing(HashTag::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public List<HashTag> getAllById(List<Integer> ids) {
        return hashTagRepository.findAllById(ids).stream()
                .sorted(Comparator.comparing(HashTag::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public boolean isExists(String keyword) {
        return hashTagRepository.findAll().stream()
                .map(HashTag::getKeyword)
                .anyMatch(keyword::equalsIgnoreCase);
    }

    @Override
    public HashTag save(String keyword) {
        if (isExists(keyword))
            throw new ResourceAlreadyExistsException("Hashtag with keyword of " + keyword + " already exists!");

        HashTag hashTag = hashTagMapper.toEntity(keyword);
        hashTagRepository.save(hashTag);
        log.debug("Saving hashtag success!");
        return hashTag;
    }
}
