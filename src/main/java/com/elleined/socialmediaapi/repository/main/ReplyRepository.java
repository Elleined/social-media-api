package com.elleined.socialmediaapi.repository.main;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    @Query("SELECT r FROM Reply r WHERE r.comment = :comment")
    Page<Reply> findAll(@Param("comment") Comment comment, Pageable pageable);
}