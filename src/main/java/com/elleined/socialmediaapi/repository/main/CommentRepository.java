package com.elleined.socialmediaapi.repository.main;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.vote.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c.votes FROM Comment c WHERE c = :comment")
    Page<Vote> findAllVotes(@Param("comment") Comment comment, Pageable pageable);

    @Query("SELECT c.replies FROM Comment c WHERE c = :comment")
    Page<Reply> findAllReplies(@Param("comment") Comment comment, Pageable pageable);

    @Query("SELECT c.reactions FROM Comment c WHERE c = :comment")
    Page<Reaction> findAllReactions(@Param("comment") Comment comment, Pageable pageable);
}