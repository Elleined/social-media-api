package com.elleined.socialmediaapi.repository.story;

import com.elleined.socialmediaapi.model.story.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Integer> {
}