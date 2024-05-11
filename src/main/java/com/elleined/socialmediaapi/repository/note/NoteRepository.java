package com.elleined.socialmediaapi.repository.note;

import com.elleined.socialmediaapi.model.note.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {
}