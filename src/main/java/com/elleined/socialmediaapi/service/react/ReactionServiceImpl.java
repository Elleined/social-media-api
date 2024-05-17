package com.elleined.socialmediaapi.service.react;

import com.elleined.socialmediaapi.exception.NotOwnedException;
import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.react.ReactMapper;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.React;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.react.ReactRepository;
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
public class ReactionServiceImpl implements ReactionService {
    private final ReactRepository reactRepository;
    private final ReactMapper reactMapper;

    @Override
    public React getById(int id) throws ResourceNotFoundException {
        return reactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reaction with id of " + id + " doesn't exists!"));
    }

    @Override
    public List<React> getAllById(Set<Integer> ids) {
        return reactRepository.findAllById(ids);
    }

    @Override
    public React save(User currentUser, Emoji emoji) {
        React react = reactMapper.toEntity(currentUser, emoji);
        reactRepository.save(react);
        log.debug("Saving react success");
        return react;
    }

    @Override
    public void update(User currentUser, React react, Emoji emoji) {
        if (react.notOwnedBy(currentUser))
            throw new NotOwnedException("Cannot update react to this post! because you don't own this reaction");
        react.setEmoji(emoji);
        reactRepository.save(react);
        log.debug("Updating react success!");
    }

    @Override
    public void delete(User currentUser, React react) {
        if (react.notOwnedBy(currentUser))
            throw new NotOwnedException("Cannot delete this post reaction! because you dont owned this reaction!");
        reactRepository.delete(react);
        log.debug("React deleted successfully.");
    }
}
