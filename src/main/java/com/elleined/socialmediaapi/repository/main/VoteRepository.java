package com.elleined.socialmediaapi.repository.main;

import com.elleined.socialmediaapi.model.main.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
}