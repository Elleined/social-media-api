package com.elleined.socialmediaapi.service.note;

import com.elleined.socialmediaapi.exception.note.NoteException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.note.NoteMapper;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.note.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService, NoteServiceRestriction {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    public Note save(User currentUser, String thought) throws NoteException {
        if (hasNote(currentUser))
            throw new NoteException("Creating note failed! because you already have note existing!");

        Note note = noteMapper.toEntity(currentUser, thought);
        noteRepository.save(note);
        log.debug("Note with id of {} saved successfully!", note.getId());
        return note;
    }

    @Override
    public Note update(User currentUser, String newThought) {
        if (doesNotHaveNote(currentUser))
            throw new NoteException("Updating note failed! because current user has no note");

        Note note = currentUser.getNote();
        note.setThought(newThought);
        note.setUpdatedAt(LocalDateTime.now());
        noteRepository.save(note);
        log.debug("Note with id of {} updated with new thought of {}", note.getId(), newThought);
        return note;
    }

    @Override
    public void delete(User currentUser, Note note) {
        if (doesNotHaveNote(currentUser))
            throw new NoteException("Deleting note failed! because current user has no note");

        noteRepository.delete(note);
        log.debug("Note with id of {} deleted successfully", note.getId());
    }

    @Override
    public Note getNote(User currentUser) {
        return currentUser.getNote();
    }

    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note getById(int id) throws ResourceNotFoundException {
        return noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note with id of " + id + " doesn't exists!"));
    }

    @Override
    public Page<Note> getAll(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }

    @Override
    public void deleteAllExpiresNotes() {
        List<Note> notes = noteRepository.findAll().stream()
                .filter(Note::isExpired)
                .toList();

        noteRepository.deleteAll(notes);
        log.trace("Expired note with ids of {} deleted successfully", notes.stream().map(Note::getId).toList());
    }
}
