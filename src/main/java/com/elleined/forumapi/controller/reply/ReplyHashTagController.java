package com.elleined.forumapi.controller.reply;

import com.elleined.forumapi.dto.CommentDTO;
import com.elleined.forumapi.dto.PostDTO;
import com.elleined.forumapi.dto.ReplyDTO;
import com.elleined.forumapi.mapper.CommentMapper;
import com.elleined.forumapi.mapper.PostMapper;
import com.elleined.forumapi.mapper.ReplyMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.hashtag.entity.EntityHashTagService;
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
