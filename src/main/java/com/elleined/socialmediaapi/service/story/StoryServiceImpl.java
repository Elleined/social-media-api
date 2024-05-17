package com.elleined.socialmediaapi.service.story;

import com.elleined.socialmediaapi.exception.story.StoryException;
import com.elleined.socialmediaapi.mapper.story.StoryMapper;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.story.StoryRepository;
import com.elleined.socialmediaapi.request.story.StoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {
    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;

    @Override
    public Story save(User currentUser, StoryRequest storyRequest) {
        Story story = storyMapper.toEntity(currentUser, storyRequest.getContent(), storyRequest.getAttachPicture());
        storyRepository.save(story);
        log.debug("saving story success");
        return story;
    }

    @Override
    public List<Story> getAll() {
        return storyRepository.findAll().stream()
                .sorted(Comparator.comparing(Story::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public List<Story> getAllById(Set<Integer> ids) {
        return storyRepository.findAllById(ids).stream()
                .sorted(Comparator.comparing(Story::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public void delete(User currentUser, Story story) {
        if (currentUser.doesNotHaveStory())
            throw new StoryException("Cannot delete story! Becuase current user doesn't have this story!");
        storyRepository.delete(story);
        log.debug("Deleting story success");
    }

    @Override
    public Story getStory(User currentUser) {
        return currentUser.getStory();
    }

    @Override
    public void deleteAllExpiredStory() {
        List<Story> stories = storyRepository.findAll().stream()
                .filter(Story::isExpired)
                .toList();

        storyRepository.deleteAll(stories);
        log.trace("Expired note with ids of {} deleted successfully", stories.stream().map(Story::getId).toList());
    }
}
