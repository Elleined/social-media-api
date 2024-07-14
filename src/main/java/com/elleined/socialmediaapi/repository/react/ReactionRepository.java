package com.elleined.socialmediaapi.repository.react;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.reaction.Reaction;
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
    Page<Reaction> findAllByComment(@Param("comment") Comment comment, Pageable pageable);

    @Query("""
            SELECT r
            FROM Reaction r
            JOIN r.posts post
            WHERE post = :post
            """)
    Page<Reaction> findAllByPost(@Param("post") Post post, Pageable pageable);
}