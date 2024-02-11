package com.elleined.forumapi.service.note;

import com.elleined.forumapi.exception.NoteException;
import com.elleined.forumapi.mapper.note.NoteMapper;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.note.Note;
import com.elleined.forumapi.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    public Note save(User currentUser, String thought) throws NoteException {
        if (currentUser.hasNote()) throw new NoteException("Creating note failed! because you already have note existing!");
        Note note = noteMapper.toEntity(currentUser, thought);
        noteRepository.save(note);
        log.debug("Note with id of {} saved successfully!", note.getId());
        return note;
    }

    @Override
    public Note update(User currentUser, String newThought) {
        if (!currentUser.hasNote()) throw new NoteException("Updating note failed! because current user has no note");
        Note note = currentUser.getNote();
        note.setThought(newThought);
        noteRepository.save(note);
        log.debug("Note with id of {} updated with new thought of {}", note.getId(), newThought);
        return note;
    }

    @Override
    public void delete(User currentUser) {
        if (!currentUser.hasNote()) throw new NoteException("Deleting note failed! because current user has no note");
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
                .filter(Note::isExpired)
                .toList();

        noteRepository.deleteAll(notes);
        log.trace("Expired note with ids of {} deleted successfully", notes.stream().map(Note::getId).toList());
    }
}
