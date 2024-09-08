package com.elleined.socialmediaapi.controller.user.note;

import com.elleined.socialmediaapi.dto.note.NoteDTO;
import com.elleined.socialmediaapi.mapper.note.NoteMapper;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.note.NoteService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/note")
public class UserNoteController {
    private final UserService userService;

    private final NoteService noteService;
    private final NoteMapper noteMapper;

    @GetMapping
    public NoteDTO getNote(@RequestHeader("Authorization") String jwt) {
        User currentUser = userService.getByJWT(jwt);
        Note note = noteService.getNote(currentUser);
        return noteMapper.toDTO(note);
    }

    @PostMapping
    public NoteDTO save(@RequestHeader("Authorization") String jwt,
                        @RequestParam("thought") String thought) {

        User currentUser = userService.getByJWT(jwt);
        Note note = noteService.save(currentUser, thought);
        return noteMapper.toDTO(note);
    }

    @PatchMapping
    public NoteDTO update(@RequestHeader("Authorization") String jwt,
                          @RequestParam("newThought") String newThought) {

        User currentUser = userService.getByJWT(jwt);
        Note note = noteService.update(currentUser, newThought);
        return noteMapper.toDTO(note);
    }

    @DeleteMapping("{noteId}")
    public void delete(@RequestHeader("Authorization") String jwt,
                       @PathVariable("noteId") int noteId) {
        User currentUser = userService.getByJWT(jwt);
        Note note = noteService.getById(noteId);
        noteService.delete(currentUser, note);
    }
}
