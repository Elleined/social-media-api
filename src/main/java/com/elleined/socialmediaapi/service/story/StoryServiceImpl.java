package com.elleined.socialmediaapi.service.story;

import com.elleined.socialmediaapi.exception.resource.ResourceAlreadyExistsException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.story.StoryException;
import com.elleined.socialmediaapi.mapper.story.StoryMapper;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.story.StoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService, StoryServiceRestriction {
    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;

    @Override
    public Story save(User currentUser, String content, MultipartFile attachedPicture, Set<Mention> mentions) {
        if (hasStory(currentUser))
            throw new ResourceAlreadyExistsException("Cannot save story because you've already created one. please delete first then create again!");

        String picture = attachedPicture == null ? null : attachedPicture.getOriginalFilename();
        Story story = storyMapper.toEntity(currentUser, content, picture, mentions);
        storyRepository.save(story);
        log.debug("saving story success");
        return story;
    }

    @Override
    public Story save(Story story) {
        return storyRepository.save(story);
    }

    @Override
    public Story getById(int id) throws ResourceNotFoundException {
        return storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story with id of " + id + " doesn't exists!"));
    }

    @Override
    public Page<Story> getAll(Pageable pageable) {
        return storyRepository.findAll(pageable);
    }

    @Override
    public void delete(User currentUser, Story story) {
        if (doesNotHaveStory(currentUser))
            throw new StoryException("Cannot delete story! because current user doesn't have this story!");

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
