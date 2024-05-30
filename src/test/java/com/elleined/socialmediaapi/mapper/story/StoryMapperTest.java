package com.elleined.socialmediaapi.mapper.story;

import com.elleined.socialmediaapi.dto.story.StoryDTO;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class StoryMapperTest {

    private final StoryMapper storyMapper = Mappers.getMapper(StoryMapper.class);

    @Test
    @DisplayName("to DTO")
    void toDTO() {
        // Pre defined values

        // Expected Value
        Story expected = Story.builder()
                .id(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .content("Content")
                .attachPicture("Attached Picture Path")
                .creator(User.builder()
                        .id(1)
                        .build())
                .mentions(new HashSet<>())
                .reactions(new HashSet<>())
                .build();

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method
        StoryDTO actual = storyMapper.toDTO(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(expected.getId(), actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getContent());

        assertNotNull(actual.getAttachPicture());

        assertEquals(1, actual.getCreatorId());
    }

    @Test
    @DisplayName("to entity")
    void toEntity() {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method
        Story actual = storyMapper.toEntity(new User(), "Content", "Attached Picture Path", new HashSet<>());

        // Behavior Verifications

        // Assertions
        assertEquals(0, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getContent());

        assertNotNull(actual.getAttachPicture());

        assertNotNull(actual.getCreator());

        assertNotNull(actual.getMentions());

        assertNotNull(actual.getReactions());
    }
}