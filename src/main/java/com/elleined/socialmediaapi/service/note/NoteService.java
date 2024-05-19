package com.elleined.socialmediaapi.service.note;

import com.elleined.socialmediaapi.exception.note.NoteException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.CustomService;

public interface NoteService extends CustomService<Note> {
    Note save(User currentUser, String thought) throws NoteException;
    Note update(User currentUser, String newThought);
    void delete(User currentUser, Note note);
    Note getNote(User currentUser);

    void deleteAllExpiresNotes();
}
