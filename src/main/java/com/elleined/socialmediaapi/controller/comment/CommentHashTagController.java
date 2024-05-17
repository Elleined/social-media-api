package com.elleined.socialmediaapi.controller.comment;

import com.elleined.socialmediaapi.dto.CommentDTO;
import com.elleined.socialmediaapi.mapper.CommentMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.hashtag.entity.EntityHashTagService;
import com.elleined.socialmediaapi.service.user.UserService;
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
