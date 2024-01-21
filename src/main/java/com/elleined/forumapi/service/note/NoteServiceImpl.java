package com.elleined.forumapi.service.note;

import com.elleined.forumapi.mapper.NoteMapper;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.note.Note;
import com.elleined.forumapi.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    public Note save(User currentUser, String thought) {
        Note note = noteMapper.toEntity(currentUser, thought);
        noteRepository.save(note);
        log.debug("Note with id of {} saved successfully!", note.getId());
    }

    @Override
    public void update(User currentUser, String newThought) {
        Note note = currentUser.getNote();
        note.setThought(newThought);
        noteRepository.save(note);
        log.debug("Note with id of {} updated with new thought of {}", note.getId(), newThought);
    }

    @Override
    public void delete(User currentUser) {
        Note note = currentUser.getNote();
        noteRepository.delete(note);
        log.debug("Note with id of {} deleted successfully", note.getId());
    }

    @Override
    public Note getNote(User currentUser) {
        return currentUser.getNote();
    }

    @Override
    public void deleteExpiredNotes() {
        List<Note> notes = noteRepository.findAll().stream()
                .filter(note -> {
                    LocalDateTime noteCreation = note.getCreatedAt();
                    LocalDateTime noteExpiration = noteCreation.plusDays(1);

                    return LocalDateTime.now().isAfter(noteExpiration) ||
                            LocalDateTime.now().equals(noteExpiration);
                })
                .toList();

        noteRepository.deleteAll(notes);
        log.debug("Expired note with ids of {} deleted successfully", notes.stream().map(Note::getId).toList());
    }
}
