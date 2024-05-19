package com.elleined.socialmediaapi.controller.user.note;

import com.elleined.socialmediaapi.dto.note.NoteDTO;
import com.elleined.socialmediaapi.mapper.note.NoteMapper;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.note.NoteService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final NoteMapper noteMapper;

    @GetMapping
    public List<NoteDTO> getAll() {
        return noteService.getAll().stream()
                .map(noteMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public NoteDTO getById(@PathVariable("id") int id) {
        Note note = noteService.getById(id);
        return noteMapper.toDTO(note);
    }

    @GetMapping("/get-all-by-id")
    public List<NoteDTO> getAllById(@RequestBody List<Integer> ids) {
        return noteService.getAllById(ids).stream()
                .map(noteMapper::toDTO)
                .toList();
    }
}
