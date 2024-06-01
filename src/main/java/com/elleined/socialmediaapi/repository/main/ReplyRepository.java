package com.elleined.socialmediaapi.repository.main;

import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Query("SELECT r.reactions FROM Reply r WHERE r = :reply")
    Page<Reaction> findAllReactions(@Param("reply") Reply reply, Pageable pageable);
}