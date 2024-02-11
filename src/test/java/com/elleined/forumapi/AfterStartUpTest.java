package com.elleined.forumapi;

import com.elleined.forumapi.populator.EmojiPopulator;
import com.elleined.forumapi.repository.EmojiRepository;
import com.elleined.forumapi.service.ModalTrackerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AfterStartUpTest {

    @Mock
    private ModalTrackerService modalTrackerService;

    @Mock
    private EmojiPopulator emojiPopulator;

    @Mock
    private EmojiRepository emojiRepository;

    @InjectMocks
    private AfterStartUp afterStartUp;

    @Test
    void init() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods
        doNothing().when(modalTrackerService).deleteAll();
        when(emojiRepository.existsById(anyInt())).thenReturn(false);
        doNothing().when(emojiPopulator).populate();

        // Calling the method
        // Assertions
        assertDoesNotThrow(() -> afterStartUp.init());

        // Behavior Verifications
        verify(modalTrackerService).deleteAll();
        verify(emojiRepository).existsById(anyInt());
        verify(emojiPopulator).populate();
    }
}