package com.elleined.socialmediaapi;

import com.elleined.socialmediaapi.populator.EmojiPopulator;
import com.elleined.socialmediaapi.repository.react.EmojiRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AfterStartUp {

    private final EmojiPopulator emojiPopulator;
    private final EmojiRepository emojiRepository;

    @PostConstruct
    void init() {
        if (emojiRepository.existsById(1)) return;
        System.out.println("Please wait populating emoji table...");
        emojiPopulator.populate();
        System.out.println("Populating emoji table success...");
    }
}
