package com.elleined.forumapi.controller.post;

import com.elleined.forumapi.dto.PostDTO;
import com.elleined.forumapi.mapper.PostMapper;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.PostLike;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.post.PostService;
import com.elleined.forumapi.service.ws.notification.like.PostWSLikeNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/posts")
public class PostLikeController {
    private final UserService userService;

    private final PostService postService;
    private final PostMapper postMapper;

    private final PostWSLikeNotificationService postLikeWSNotificationService;

    @PatchMapping("/like/{postId}")
    public PostDTO like(@PathVariable("currentUserId") int respondentId,
                        @PathVariable("postId") int postId) {

        User respondent = userService.getById(respondentId);
        Post post = postService.getById(postId);

        if (postService.isLiked(respondent, post)) {
            postService.unLike(respondent, post);
            return postMapper.toDTO(post);
        }

        PostLike postLike = postService.like(respondent, post);
        postLikeWSNotificationService.broadcast(postLike);
        return postMapper.toDTO(post);
    }
}
