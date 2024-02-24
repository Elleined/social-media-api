package com.elleined.socialmediaapi.service.hashtag.entity;

import com.elleined.socialmediaapi.mapper.hashtag.HashTagMapper;
import com.elleined.socialmediaapi.model.Reply;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.repository.HashTagRepository;
import com.elleined.socialmediaapi.repository.ReplyRepository;
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
public class ReplyHashTagService implements EntityHashTagService<Reply> {
    private final HashTagRepository hashTagRepository;

    private final HashTagService hashTagService;
    private final HashTagMapper hashTagMapper;

    private final ReplyRepository replyRepository;

    @Override
    public Set<Reply> getAllByHashTagKeyword(User currentUser, String keyword) {
        return hashTagRepository.getAllReplyByHashTagKeyword(keyword).stream()
                .filter(reply -> !reply.getReplier().equals(currentUser))
                .collect(Collectors.toSet());
    }


    @Override
    public HashTag save(Reply reply, String keyword) {
        if (hashTagService.notExist(keyword)) {
            HashTag hashTag = hashTagMapper.toEntity(keyword);

            reply.getHashTags().add(hashTag);
            hashTag.getReplies().add(reply);

            hashTagService.save(hashTag);
            replyRepository.save(reply);
            log.debug("HashTag with keyword of {} saved successfully", keyword);
            return hashTag;
        }

        HashTag hashTag = hashTagService.getByKeyword(keyword);

        reply.getHashTags().add(hashTag);
        hashTag.getReplies().add(reply);

        hashTagService.save(hashTag);
        replyRepository.save(reply);
        log.debug("HashTag with keyword of {} saved successfully", keyword);
        return hashTag;
    }

    @Override
    public List<HashTag> saveAll(Reply reply, Set<String> keywords) {
        return keywords.stream()
                .map(keyword -> this.save(reply, keyword))
                .toList();
    }
}
