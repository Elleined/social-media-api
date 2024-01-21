package com.elleined.forumapi.service.note;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.note.Note;
import com.elleined.forumapi.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    @Override
    public Note save(User currentUser, String thought) {
        return null;
    }

    @Override
    public void update(User currentUser, String newThought) {

    }

    @Override
    public void delete(User currentUser) {

    }

    @Override
    public Note getNote(User currentUser) {
        return null;
    }
}
