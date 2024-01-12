package com.elleined.forumapi.controller.comment;

import com.elleined.forumapi.dto.CommentDTO;
import com.elleined.forumapi.mapper.CommentMapper;
import com.elleined.forumapi.mapper.ReplyMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.hashtag.entity.EntityHashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/posts/comments/hashtags")
public class CommentHashTagController {


    private final UserService userService;

    private final EntityHashTagService<Comment> commentHashTagService;

    private final CommentMapper commentMapper;

    @GetMapping("/search-comments-by-keyword")
    public Set<CommentDTO> searchPostByHashtagKeyword(@PathVariable("currentUserId") int currentUserId,
                                                      @RequestParam("keyword") String keyword) {
        User currentUser = userService.getById(currentUserId);

        return commentHashTagService.getAllByHashTagKeyword(currentUser, keyword).stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toSet());
    }
}
