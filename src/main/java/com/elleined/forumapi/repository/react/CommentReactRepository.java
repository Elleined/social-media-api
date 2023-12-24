package com.elleined.forumapi.repository.react;

import com.elleined.forumapi.model.react.CommentReact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReactRepository extends JpaRepository<CommentReact, Integer> {
}