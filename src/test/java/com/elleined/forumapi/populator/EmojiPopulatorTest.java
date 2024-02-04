package com.elleined.forumapi.populator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.forumapi.repository.EmojiRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.annotation.Inherited;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class EmojiPopulatorTest {

    @Mock
    private EmojiRepository emojiRepository;

    @InjectMocks
    private EmojiPopulator emojiPopulator;

    @Test
    void populate() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods
        when(emojiRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        // Calling the method
        // Assertions
        assertDoesNotThrow(() -> emojiPopulator.populate());

        // Behavior Verifications
        verify(emojiRepository).saveAll(anyList());
    }
}