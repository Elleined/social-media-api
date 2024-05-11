package com.elleined.socialmediaapi.populator;

import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.repository.react.EmojiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class EmojiPopulator implements Populator {

    private final EmojiRepository emojiRepository;

    @Override
    public void populate() {
        List<Emoji> emojis = Arrays.stream(Emoji.Type.values())
                .map(Emoji::new)
                .toList();

        emojiRepository.saveAll(emojis);
    }
}
