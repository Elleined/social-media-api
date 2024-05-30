package com.elleined.socialmediaapi.mapper.mention;

import com.elleined.socialmediaapi.dto.mention.MentionDTO;
import com.elleined.socialmediaapi.model.mention.Mention;
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
class MentionMapperTest {

    private final MentionMapper mentionMapper = Mappers.getMapper(MentionMapper.class);

    @Test
    @DisplayName("to DTO")
    void toDTO() {
        // Pre defined values

        // Expected Value

        // Mock data
        Mention expected = Mention.builder()
                .id(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .creator(User.builder()
                        .id(1)
                        .build())
                .mentionedUser(User.builder()
                        .id(1)
                        .build())
                .posts(new HashSet<>())
                .comments(new HashSet<>())
                .replies(new HashSet<>())
                .stories(new HashSet<>())
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        MentionDTO actual = mentionMapper.toDTO(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(expected.getId(), actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertEquals(1, actual.getCreatorId());

        assertEquals(1, actual.getMentionedUserId());

        assertNotNull(actual.getPostIds());

        assertNotNull(actual.getCommentIds());

        assertNotNull(actual.getReplyIds());
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
        Mention actual = mentionMapper.toEntity(new User(), new User());

        // Behavior Verifications

        // Assertions
        assertEquals(0, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getCreator());

        assertNotNull(actual.getMentionedUser());

        assertNotNull(actual.getPosts());

        assertNotNull(actual.getComments());

        assertNotNull(actual.getAllReplyIds());
    }
}