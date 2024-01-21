package com.elleined.forumapi.repository;

import com.elleined.forumapi.model.note.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {
}