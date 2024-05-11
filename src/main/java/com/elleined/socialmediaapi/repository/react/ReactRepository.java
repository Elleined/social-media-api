package com.elleined.socialmediaapi.repository.react;

import com.elleined.socialmediaapi.model.react.React;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactRepository extends JpaRepository<React, Integer> {
}