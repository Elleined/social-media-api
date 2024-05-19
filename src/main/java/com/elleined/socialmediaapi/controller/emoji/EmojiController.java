package com.elleined.socialmediaapi.controller.emoji;

import com.elleined.socialmediaapi.dto.reaction.EmojiDTO;
import com.elleined.socialmediaapi.mapper.emoji.EmojiMapper;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.service.emoji.EmojiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/emojis")
public class EmojiController {
    private final EmojiService emojiService;
    private final EmojiMapper emojiMapper;

    @GetMapping
    public List<EmojiDTO> getAll() {
        return emojiService.getAll().stream()
                .map(emojiMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public EmojiDTO getById(@PathVariable("id") int id) {
        Emoji emoji = emojiService.getById(id);
        return emojiMapper.toDTO(emoji);
    }

    @GetMapping("/get-all-by-id")
    public List<EmojiDTO> getAllById(@RequestBody List<Integer> ids) {
        return emojiService.getAllById(ids).stream()
                .map(emojiMapper::toDTO)
                .toList();
    }

}
