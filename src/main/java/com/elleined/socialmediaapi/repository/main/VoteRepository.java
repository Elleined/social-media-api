package com.elleined.socialmediaapi.repository.main;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.vote.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.comment = :comment")
    Page<Vote> findAll(@Param("comment") Comment comment, Pageable pageable);

    @Query("SELECT v FROM Vote v WHERE v.comment = :comment AND v.verdict = :verdict")
    Page<Vote> findAll(@Param("comment") Comment comment,
                       @Param("verdict") Vote.Verdict verdict,
                       Pageable pageable);
}