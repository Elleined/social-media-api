package com.elleined.forumapi.controller.user.note;

import com.elleined.forumapi.dto.NoteDTO;
import com.elleined.forumapi.mapper.note.NoteMapper;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.note.Note;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{currentUserId}/notes")
@RequiredArgsConstructor
public class NoteController {
    private final UserService userService;

    private final NoteService noteService;
    private final NoteMapper noteMapper;

    public NoteDTO save(@PathVariable("currentUserId") int currentUserId,
                        @RequestParam("thought") String thought) {

        User currentUser = userService.getById(currentUserId);
        Note note = noteService.save(currentUser, thought);
        return noteMapper.toDTO(note);
    }

    public void update(@PathVariable("currentUserId") int currentUserId,
                       @RequestParam("newThought") String newThought) {

        User currentUser = userService.getById(currentUserId);
        noteService.update(currentUser, newThought);
    }

    public void delete(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        noteService.delete(currentUser);
    }

    public NoteDTO getNote(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        Note note = noteService.getNote(currentUser);
        return noteMapper.toDTO(note);
    }
}
