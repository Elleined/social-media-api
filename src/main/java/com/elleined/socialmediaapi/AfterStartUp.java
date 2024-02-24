package com.elleined.socialmediaapi;

import com.elleined.socialmediaapi.populator.EmojiPopulator;
import com.elleined.socialmediaapi.repository.EmojiRepository;
import com.elleined.socialmediaapi.service.ModalTrackerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AfterStartUp {

    private final ModalTrackerService modalTrackerService;
    private final EmojiPopulator emojiPopulator;
    private final EmojiRepository emojiRepository;

    @PostConstruct
    void init() {
        modalTrackerService.deleteAll();
        if (emojiRepository.existsById(1)) return;
        System.out.println("Please wait populating emoji table...");
        emojiPopulator.populate();
        System.out.println("Populating emoji table success...");
    }
}
