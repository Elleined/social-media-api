package com.elleined.socialmediaapi.service.emoji;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.repository.react.EmojiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmojiServiceImpl implements EmojiService {
    private final EmojiRepository emojiRepository;

    @Override
    public List<Emoji> getAll(Pageable pageable) {
        return emojiRepository.findAll(pageable).stream()
                .sorted(Comparator.comparing(Emoji::getType))
                .toList();
    }

    @Override
    public Emoji save(Emoji emoji) {
        return emojiRepository.save(emoji);
    }

    @Override
    public Emoji getById(int id) throws ResourceNotFoundException {
        return emojiRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Emoji with id of " + id + " doesn't exists"));
    }

    @Override
    public List<Emoji> getAllById(List<Integer> ids) {
        return emojiRepository.findAllById(ids).stream()
                .sorted(Comparator.comparing(Emoji::getType))
                .toList();
    }
}
