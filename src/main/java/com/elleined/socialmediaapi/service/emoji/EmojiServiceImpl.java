package com.elleined.socialmediaapi.service.emoji;

import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.repository.EmojiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmojiServiceImpl implements EmojiService {
    private final EmojiRepository emojiRepository;

    @Override
    public List<Emoji> getAll() {
        return emojiRepository.findAll();
    }

    @Override
    public Emoji getById(int id) throws ResourceNotFoundException {
        return emojiRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Emoji with id of " + id + " doesn't exists"));
    }

    @Override
    public Emoji getByType(Emoji.Type type) {
        return emojiRepository.findAll().stream()
                .filter(emoji -> emoji.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Emoji with type of " + type + " doesn't exists"));
    }
}
