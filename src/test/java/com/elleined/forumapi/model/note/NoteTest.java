package com.elleined.forumapi.model.note;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class NoteTest {

    @Test
    void isExpired() {
        // Expected Value

        // Mock data
        Note expiredNote1 = Note.builder()
                .createdAt(LocalDateTime.now().minusDays(10))
                .build();

        Note expiredNote2 = Note.builder()
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        assertTrue(expiredNote1.isExpired());
        assertTrue(expiredNote2.isExpired());

        // Behavior Verifications

        // Assertions
    }
}