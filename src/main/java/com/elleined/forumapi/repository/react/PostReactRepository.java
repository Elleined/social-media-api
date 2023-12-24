package com.elleined.forumapi.repository.react;

import com.elleined.forumapi.model.react.PostReact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReactRepository extends JpaRepository<PostReact, Integer> {
}