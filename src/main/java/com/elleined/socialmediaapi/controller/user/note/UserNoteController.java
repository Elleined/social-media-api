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
@RequestMapping("/users/{currentUserId}/note")
public class UserNoteController {
    private final UserService userService;

    private final NoteService noteService;
    private final NoteMapper noteMapper;

    @GetMapping
    public NoteDTO getNote(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        Note note = noteService.getNote(currentUser);
        return noteMapper.toDTO(note);
    }

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

    @DeleteMapping("{noteId}")
    public void delete(@PathVariable("currentUserId") int currentUserId,
                       @PathVariable("noteId") int noteId) {
        User currentUser = userService.getById(currentUserId);
        Note note = noteService.getById(noteId);
        noteService.delete(currentUser, note);
    }
}
