package com.elleined.socialmediaapi.service.note;

import com.elleined.socialmediaapi.exception.note.NoteException;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.user.User;

public interface NoteService {
    Note save(User currentUser, String thought) throws NoteException;
    Note update(User currentUser, String newThought);
    void delete(User currentUser, Note note);
    Note getNote(User currentUser);

    void deleteAllExpiresNotes();
}
