package com.elleined.socialmediaapi.repository.story;

import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.story.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoryRepository extends JpaRepository<Story, Integer> {

    @Query("SELECT s.reactions FROM Story s WHERE s = :story")
    Page<Reaction> findAllReactions(@Param("story") Story story, Pageable pageable);
}