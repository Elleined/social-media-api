package com.elleined.forumapi.controller.user.note;

import com.elleined.forumapi.mapper.NoteMapper;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.note.Note;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{currentUserId}/notes")
@RequiredArgsConstructor
public class NoteController {
    private final UserService userService;

    private final NoteService noteService;
    private final NoteMapper noteMapper;

    public Note save(User currentUser, String thought) {
        return null;
    }

    public void update(User currentUser, String newThought) {

    }

    public void delete(User currentUser) {

    }

    public Note getNote(User currentUser) {
        return null;
    }

    public void deleteExpiredNotes() {

    }
}
