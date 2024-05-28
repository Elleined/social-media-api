package com.elleined.socialmediaapi.controller.user.note;

import com.elleined.socialmediaapi.dto.note.NoteDTO;
import com.elleined.socialmediaapi.mapper.note.NoteMapper;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.service.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final NoteMapper noteMapper;

    @GetMapping
    public List<NoteDTO> getAll(@RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return noteService.getAll(pageable).stream()
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
