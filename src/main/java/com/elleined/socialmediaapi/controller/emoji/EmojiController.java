package com.elleined.socialmediaapi.controller.emoji;

import com.elleined.socialmediaapi.dto.react.EmojiDTO;
import com.elleined.socialmediaapi.mapper.emoji.EmojiMapper;
import com.elleined.socialmediaapi.service.emoji.EmojiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/forum/emojis")
public class EmojiController {
    private final EmojiService emojiService;
    private final EmojiMapper emojiMapper;

    @GetMapping
    public List<EmojiDTO> getAll() {
        return emojiService.getAll().stream()
                .map(emojiMapper::toDTO)
                .toList();
    }
}
