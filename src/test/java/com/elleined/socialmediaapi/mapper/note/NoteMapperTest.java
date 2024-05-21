package com.elleined.socialmediaapi.mapper.note;

import com.elleined.socialmediaapi.dto.note.NoteDTO;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class NoteMapperTest {

    private final NoteMapper noteMapper = Mappers.getMapper(NoteMapper.class);

    @Test
    @DisplayName("to DTO")
    void toDTO() {
        // Pre defined values

        // Expected Value
        Note expected = Note.builder()
                .id(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .thought("Thought")
                .creator(User.builder()
                        .id(1)
                        .build())
                .build();

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method
        NoteDTO actual = noteMapper.toDTO(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(expected.getId(), actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getThought());

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
        Note actual = noteMapper.toEntity(new User(), "Thought");

        // Behavior Verifications

        // Assertions
        assertEquals(0, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getThought());

        assertNotNull(actual.getCreator());
    }
}