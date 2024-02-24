package com.elleined.socialmediaapi.repository.react;

import com.elleined.socialmediaapi.model.react.PostReact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReactRepository extends JpaRepository<PostReact, Integer> {
}