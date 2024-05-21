package com.elleined.socialmediaapi.mapper.react;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.socialmediaapi.dto.reaction.ReactionDTO;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
class ReactionMapperTest {

    private final ReactionMapper reactionMapper = Mappers.getMapper(ReactionMapper.class);

    @Test
    @DisplayName("to DTO")
    void toDTO() {
        // Pre defined values

        // Expected Value
        Reaction expected = Reaction.builder()
                .id(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .creator(User.builder()
                        .id(1)
                        .build())
                .emoji(Emoji.builder()
                        .id(1)
                        .build())
                .posts(new HashSet<>())
                .comments(new HashSet<>())
                .replies(new HashSet<>())
                .notifications(new HashSet<>())
                .build();

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method
        ReactionDTO actual = reactionMapper.toDTO(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(expected.getId(), actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertEquals(1, actual.getCreatorId());

        assertEquals(1, actual.getEmojiId());

        assertNotNull(actual.getPostIds());

        assertNotNull(actual.getCommentIds());

        assertNotNull(actual.getReplyIds());

        assertNotNull(actual.getNotificationIds());
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
        Reaction actual = reactionMapper.toEntity(new User(), new Emoji());

        // Behavior Verifications

        // Assertions
        assertEquals(0, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getCreator());

        assertNotNull(actual.getEmoji());

        assertNotNull(actual.getPosts());

        assertNotNull(actual.getComments());

        assertNotNull(actual.getReplies());

        assertNotNull(actual.getNotifications());
    }
}