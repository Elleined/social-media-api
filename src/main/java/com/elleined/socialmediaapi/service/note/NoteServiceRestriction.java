package com.elleined.socialmediaapi.service.note;

import com.elleined.socialmediaapi.model.user.User;

public interface NoteServiceRestriction {

    default boolean hasNote(User currentUser) {
        return currentUser.getNote() != null;
    }

    default boolean doesNotHaveNote(User currentUser) {
        return currentUser.getNote() == null;
    }
}
