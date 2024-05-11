package com.elleined.socialmediaapi.repository.main;

import com.elleined.socialmediaapi.model.main.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Integer> {
}