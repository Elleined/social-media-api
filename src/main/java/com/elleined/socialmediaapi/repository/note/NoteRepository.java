package com.elleined.socialmediaapi.repository.note;

import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    @Query("SELECT n.reactions FROM Note n WHERE n = :note")
    Page<Reaction> findAllReactions(@Param("note") Note note, Pageable pageable);
}