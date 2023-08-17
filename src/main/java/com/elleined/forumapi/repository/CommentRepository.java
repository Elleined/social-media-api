package com.elleined.forumapi.repository;

import com.elleined.forumapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Integer> {
}