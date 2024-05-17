package com.elleined.socialmediaapi.scheduler;

import com.elleined.socialmediaapi.service.story.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class StoryScheduler {
    private final StoryService storyService;

    @Scheduled(fixedRate = 2000L)
    public void deleteExpiredNotes() {
        storyService.deleteAllExpiredStory();
    }
}
