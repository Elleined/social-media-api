package com.elleined.socialmediaapi.mapper.emoji;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.socialmediaapi.dto.reaction.EmojiDTO;
import com.elleined.socialmediaapi.model.react.Emoji;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class EmojiMapperTest {
    private final EmojiMapper emojiMapper = Mappers.getMapper(EmojiMapper.class);

    @Test
    @DisplayName("to DTO")
    void toDTO() {
        // Expected Value
        Emoji expected = Emoji.builder()
                .id(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .type(Emoji.Type.ANGRY.name())
                .build();

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method
        EmojiDTO actual = emojiMapper.toDTO(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(expected.getId(), actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getType());
        assertEquals(expected.getType(), actual.getType());
    }

    @Test
    @DisplayName("to entity")
    void toEntity() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method
        Emoji actual = emojiMapper.toEntity("Emoji Type");

        // Behavior Verifications

        // Assertions
        assertEquals(0, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getType());
    }
}