package com.elleined.forumapi.service.emoji;

import com.elleined.forumapi.model.react.Emoji;
import com.elleined.forumapi.model.react.Emoji.Type;
import com.elleined.forumapi.repository.EmojiRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmojiServiceImplTest {

    @Mock
    private EmojiRepository emojiRepository;

    @InjectMocks
    private EmojiServiceImpl emojiService;

    @Test
    void getAll() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods
        when(emojiRepository.findAll()).thenReturn(new ArrayList<>());

        // Calling the method
        // Assertions
        assertEquals(0, emojiService.getAll().size());

        // Behavior Verifications
        verify(emojiRepository).findAll();
    }

    @Test
    void getById() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods
        when(emojiRepository.findById(anyInt())).thenReturn(Optional.of(new Emoji()));

        // Calling the method
        // Assertions
        assertDoesNotThrow(() -> emojiService.getById(anyInt()));

        // Behavior Verifications
        verify(emojiRepository).findById(anyInt());
    }

    @ParameterizedTest
    @ValueSource(strings = {"HAHA", "SAD", "ANGRY", "WOW", "CARE", "LIKE", "HEART",})
    void getByType(String emojiType) {
        // Expected and Actual Value
        // Mock Data

        Emoji hahaEmoji = Emoji.builder()
                .type(Type.HAHA)
                .build();

        Emoji sadEmoji = Emoji.builder()
                .type(Type.SAD)
                .build();

        Emoji angryEmoji = Emoji.builder()
                .type(Type.ANGRY)
                .build();

        Emoji wowEmoji = Emoji.builder()
                .type(Type.WOW)
                .build();

        Emoji careEmoji = Emoji.builder()
                .type(Type.CARE)
                .build();

        Emoji likeEmoji = Emoji.builder()
                .type(Type.LIKE)
                .build();

        Emoji heartEmoji = Emoji.builder()
                .type(Type.HEART)
                .build();

        // Stubbing methods
        when(emojiRepository.findAll()).thenReturn(List.of(hahaEmoji, sadEmoji, angryEmoji, wowEmoji, careEmoji, likeEmoji, heartEmoji));

        // Calling the method
        // Assertions
        Emoji selectedEmoji = emojiService.getByType(Type.valueOf(emojiType));

        assertNotNull(selectedEmoji);
        assertEquals(emojiType, selectedEmoji.getType().name());

        // Behavior Verifications
        verify(emojiRepository).findAll();
    }
}