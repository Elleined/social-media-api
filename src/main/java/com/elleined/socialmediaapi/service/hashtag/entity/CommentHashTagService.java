package com.elleined.socialmediaapi.service.hashtag.entity;

import com.elleined.socialmediaapi.mapper.hashtag.HashTagMapper;
import com.elleined.socialmediaapi.model.Comment;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.repository.CommentRepository;
import com.elleined.socialmediaapi.repository.HashTagRepository;
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
@RequiredArgsConstructor
@Transactional
public class CommentHashTagService implements EntityHashTagService<Comment> {
    private final HashTagRepository hashTagRepository;

    private final HashTagService hashTagService;
    private final HashTagMapper hashTagMapper;

    private final CommentRepository commentRepository;

    @Override
    public Set<Comment> getAllByHashTagKeyword(User currentUser, String keyword) {
        return hashTagRepository.getAllCommentByHashTagKeyword(keyword).stream()
                .filter(comment -> !comment.getCommenter().equals(currentUser))
                .collect(Collectors.toSet());
    }


    @Override
    public HashTag save(Comment comment, String keyword) {
        if (hashTagService.notExist(keyword)) {
            HashTag hashTag = hashTagMapper.toEntity(keyword);

            comment.getHashTags().add(hashTag);
            hashTag.getComments().add(comment);

            hashTagService.save(hashTag);
            commentRepository.save(comment);
            log.debug("HashTag with keyword of {} saved successfully", keyword);
            return hashTag;
        }

        HashTag hashTag = hashTagService.getByKeyword(keyword);

        comment.getHashTags().add(hashTag);
        hashTag.getComments().add(comment);

        hashTagService.save(hashTag);
        commentRepository.save(comment);
        log.debug("HashTag with keyword of {} saved successfully", keyword);
        return hashTag;
    }

    @Override
    public List<HashTag> saveAll(Comment comment, Set<String> keywords) {
        return keywords.stream()
                .map(keyword -> this.save(comment, keyword))
                .toList();
    }
}
