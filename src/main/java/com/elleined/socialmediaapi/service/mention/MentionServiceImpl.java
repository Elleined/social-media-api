package com.elleined.socialmediaapi.service.mention;

import com.elleined.socialmediaapi.mapper.mention.MentionMapper;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.mention.MentionRepository;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MentionServiceImpl implements MentionService {
    private final UserRepository userRepository;

    private final BlockService blockService;

    private final MentionRepository mentionRepository;
    private final MentionMapper mentionMapper;

    @Override
    public Mention save(User mentioningUser, User mentionedUser) {
        Mention mention = mentionMapper.toEntity(mentioningUser, mentionedUser);
        mentionRepository.save(mention);
        log.debug("Saving mention success");
        return mention;
    }

    @Override
    public List<Mention> getAllById(Set<Integer> ids) {
        return mentionRepository.findAllById(ids);
    }

    @Override
    public List<User> getSuggestedMentions(User currentUser, String name) {
        return userRepository.fetchAllByProperty(name).stream()
                .filter(suggestedUser -> !suggestedUser.equals(currentUser))
                .filter(suggestedUser -> !blockService.isBlockedBy(currentUser, suggestedUser))
                .filter(suggestedUser -> !blockService.isYouBeenBlockedBy(currentUser, suggestedUser))
                .toList();
    }
}
