package com.elleined.forumapi.repository.react;

import com.elleined.forumapi.model.react.React;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactRepository extends JpaRepository<React, Integer> {
}