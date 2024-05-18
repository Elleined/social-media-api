package com.elleined.socialmediaapi.service.mention;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.mention.MentionMapper;
import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.mention.MentionRepository;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
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
    public List<User> getAllSuggestedMentions(User currentUser, String name) {
        return userRepository.fetchAllByProperty(name).stream()
                .filter(suggestedUser -> !suggestedUser.equals(currentUser))
                .filter(suggestedUser -> !blockService.isBlockedByYou(currentUser, suggestedUser))
                .filter(suggestedUser -> !blockService.isYouBeenBlockedBy(currentUser, suggestedUser))
                .toList();
    }

    @Override
    public Mention save(Mention mention) {
        return mentionRepository.save(mention);
    }

    @Override
    public Mention getById(int id) throws ResourceNotFoundException {
        return mentionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mention with id of " + id + " doesn't exists!"));
    }

    @Override
    public List<Mention> getAll() {
        return mentionRepository.findAll().stream()
                .sorted(Comparator.comparing(PrimaryKeyIdentity::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public List<Mention> getAllById(List<Integer> ids) {
        return mentionRepository.findAllById(ids).stream()
                .sorted(Comparator.comparing(PrimaryKeyIdentity::getCreatedAt).reversed())
                .toList();
    }
}
