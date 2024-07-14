package com.elleined.socialmediaapi.repository.react;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.story.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReactionRepository extends JpaRepository<Reaction, Integer> {

    @Query("""
            SELECT r
            FROM Reaction r
            JOIN r.comments comment
            WHERE comment = :comment
            """)
    Page<Reaction> findAll(@Param("comment") Comment comment, Pageable pageable);

    @Query("""
            SELECT r
            FROM Reaction r
            JOIN r.posts post
            WHERE post = :post
            """)
    Page<Reaction> findAll(@Param("post") Post post, Pageable pageable);

    @Query("""
            SELECT r
            FROM Reaction r
            JOIN r.replies reply
            WHERE reply = :reply
            """)
    Page<Reaction> findAll(@Param("reply") Reply reply, Pageable pageable);

    @Query("""
            SELECT r
            FROM Reaction r
            JOIN r.notes note
            WHERE note = :note
            """)
    Page<Reaction> findAll(@Param("note") Note note, Pageable pageable);

    @Query("""
            SELECT r
            FROM Reaction r
            JOIN r.stories story
            WHERE story = :story
            """)
    Page<Reaction> findAll(@Param("story") Story story, Pageable pageable);
}