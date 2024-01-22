package com.elleined.forumapi.controller.user.note;

import com.elleined.forumapi.dto.NoteDTO;
import com.elleined.forumapi.mapper.note.NoteMapper;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.note.Note;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{currentUserId}/note")
@RequiredArgsConstructor
public class NoteController {
    private final UserService userService;

    private final NoteService noteService;
    private final NoteMapper noteMapper;

    @PostMapping
    public NoteDTO save(@PathVariable("currentUserId") int currentUserId,
                        @RequestParam("thought") String thought) {

        User currentUser = userService.getById(currentUserId);
        Note note = noteService.save(currentUser, thought);
        return noteMapper.toDTO(note);
    }

    @PatchMapping
    public NoteDTO update(@PathVariable("currentUserId") int currentUserId,
                       @RequestParam("newThought") String newThought) {

        User currentUser = userService.getById(currentUserId);
        Note note = noteService.update(currentUser, newThought);
        return noteMapper.toDTO(note);
    }

    @DeleteMapping
    public ResponseEntity<Note> delete(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        noteService.delete(currentUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public NoteDTO getNote(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        Note note = noteService.getNote(currentUser);
        return noteMapper.toDTO(note);
    }
}
