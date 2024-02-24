package com.elleined.socialmediaapi.repository;

import com.elleined.socialmediaapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Integer> {
}