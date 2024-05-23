package com.elleined.socialmediaapi.controller.emoji;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.dto.reaction.EmojiDTO;
import com.elleined.socialmediaapi.mapper.emoji.EmojiMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.service.emoji.EmojiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(EmojiController.class)
class EmojiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmojiService emojiService;

    @MockBean
    private EmojiMapper emojiMapper;


    @Test
    void getAll() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(emojiService.getAll()).thenReturn(List.of(new Emoji()));
        when(emojiMapper.toDTO(any(Emoji.class))).thenReturn(new EmojiDTO());

        // Calling the method
        mockMvc.perform(get("/emojis"))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(emojiService).getAll();
        verify(emojiMapper).toDTO(any(Emoji.class));

        // Assertions
    }

    @Test
    void getById() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(emojiService.getById(anyInt())).thenReturn(new Emoji());
        when(emojiMapper.toDTO(any(Emoji.class))).thenReturn(new EmojiDTO());

        // Calling the method
        mockMvc.perform(get("/emojis/{emojiId}", 1))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(emojiService).getById(anyInt());
        verify(emojiMapper).toDTO(any(Emoji.class));

        // Assertions
    }

    @Test
    void getAllById() throws Exception {
        // Pre defined values
        List<Integer> ids = List.of(1, 2);
        // Expected Value

        // Mock data

        // Set up method
        String json = objectMapper.writeValueAsString(ids);

        // Stubbing methods
        when(emojiService.getAllById(anyList())).thenReturn(List.of(new Emoji()));
        when(emojiMapper.toDTO(any(Emoji.class))).thenReturn(new EmojiDTO());

        // Calling the method
        mockMvc.perform(get("/emojis/get-all-by-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(emojiService).getAllById(anyList());
        verify(emojiMapper, atLeastOnce()).toDTO(any(Emoji.class));

        // Assertions
    }
}