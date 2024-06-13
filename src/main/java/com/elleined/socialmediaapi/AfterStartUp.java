package com.elleined.socialmediaapi;

import com.elleined.socialmediaapi.populator.EmojiPopulator;
import com.elleined.socialmediaapi.populator.UserPopulator;
import com.elleined.socialmediaapi.repository.react.EmojiRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AfterStartUp {

    private final EmojiPopulator emojiPopulator;
    private final UserPopulator userPopulator;

    private final EmojiRepository emojiRepository;

    @PostConstruct
    void init() {
        if (emojiRepository.existsById(1)) return;
        System.out.println("Please wait populating emoji and user table...");
        emojiPopulator.populate();
        userPopulator.populate();
        System.out.println("Populating emoji and user table success...");
    }
}
