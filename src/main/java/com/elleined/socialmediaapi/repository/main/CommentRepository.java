package com.elleined.socialmediaapi.repository.main;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("""
            SELECT c
            FROM Comment c
            WHERE c.post = :post
            """)
    Page<Comment> findAll(@Param("post") Post post, Pageable pageable);
}