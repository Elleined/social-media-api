package com.elleined.socialmediaapi.scheduler;

import com.elleined.socialmediaapi.service.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class NoteScheduler {
    private final NoteService noteService;

    @Scheduled(fixedRate = 2000L)
    public void deleteExpiredNotes() {
        noteService.deleteAllExpiresNotes();
    }
}
