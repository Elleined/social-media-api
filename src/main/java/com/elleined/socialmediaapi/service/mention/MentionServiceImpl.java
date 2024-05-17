package com.elleined.socialmediaapi.service.mention;

import com.elleined.socialmediaapi.mapper.mention.MentionMapper;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.mention.MentionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MentionServiceImpl implements MentionService {
    private final MentionRepository mentionRepository;
    private final MentionMapper mentionMapper;

    @Override
    public Mention save(User mentioningUser, User mentionedUser) {
        Mention mention = mentionMapper.toEntity(mentioningUser, mentionedUser);
        mentionRepository.save(mention);
        log.debug("Saving mention success");
        return mention;
    }
}
