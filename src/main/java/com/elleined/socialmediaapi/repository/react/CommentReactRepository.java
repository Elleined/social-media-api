package com.elleined.socialmediaapi.repository.react;

import com.elleined.socialmediaapi.model.react.CommentReact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReactRepository extends JpaRepository<CommentReact, Integer> {
}