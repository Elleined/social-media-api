package com.elleined.socialmediaapi.controller.reply;

import com.elleined.socialmediaapi.dto.ReplyDTO;
import com.elleined.socialmediaapi.mapper.ReplyMapper;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.hashtag.entity.EntityHashTagService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/posts/comments/replies/hashtags")
public class ReplyHashTagController {

    private final UserService userService;

    private final EntityHashTagService<Reply> replyHashTagService;

    private final ReplyMapper replyMapper;

    @GetMapping("/search-replies-by-keyword")
    public Set<ReplyDTO> searchPostByHashtagKeyword(@PathVariable("currentUserId") int currentUserId,
                                                    @RequestParam("keyword") String keyword) {
        User currentUser = userService.getById(currentUserId);

        return replyHashTagService.getAllByHashTagKeyword(currentUser, keyword).stream()
                .map(replyMapper::toDTO)
                .collect(Collectors.toSet());
    }
}
