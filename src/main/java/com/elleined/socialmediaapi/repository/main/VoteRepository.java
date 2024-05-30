package com.elleined.socialmediaapi.repository.main;

import com.elleined.socialmediaapi.model.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
}