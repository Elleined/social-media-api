package com.elleined.socialmediaapi.repository.react;

import com.elleined.socialmediaapi.model.reaction.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Integer> {
}