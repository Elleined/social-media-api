package com.elleined.forumapi.service.note;

import com.elleined.forumapi.exception.NoteException;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.note.Note;

public interface NoteService {
    Note save(User currentUser, String thought) throws NoteException;
    Note update(User currentUser, String newThought);
    void delete(User currentUser);
    Note getNote(User currentUser);

    void deleteExpiredNotes();
}
