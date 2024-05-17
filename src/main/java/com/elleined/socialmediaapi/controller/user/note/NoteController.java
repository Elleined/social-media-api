package com.elleined.socialmediaapi.controller.user.note;

import com.elleined.socialmediaapi.dto.NoteDTO;
import com.elleined.socialmediaapi.mapper.note.NoteMapper;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.user.UserService;
import com.elleined.socialmediaapi.service.note.NoteService;
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
