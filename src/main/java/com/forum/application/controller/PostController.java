package com.forum.application.controller;

import com.forum.application.dto.PostDTO;
import com.forum.application.dto.notification.PostNotification;
import com.forum.application.service.ForumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/posts")
public class PostController {
    private final ForumService forumService;
    @GetMapping
    public List<PostDTO> getAllPost(@PathVariable("currentUserId") int currentUserId) {
        return forumService.getAllPost(currentUserId);
    }

    @GetMapping("/author")
    public List<PostDTO> getAllByAuthorId(@PathVariable("currentUserId") int authorId) {
        return forumService.getAllByAuthorId(authorId);
    }


    @PostMapping
    public PostDTO savePost(@PathVariable("currentUserId") int currentUserId,
                            @RequestParam("body") String body,
                            @RequestParam(required = false, name = "attachedPicture") String attachedPicture,
                            @RequestParam(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds) {

        return forumService.savePost(currentUserId, body, attachedPicture, mentionedUserIds);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostDTO> deletePost(@PathVariable("currentUserId") int currentUserId,
                                              @PathVariable("postId") int postId) {

        forumService.deletePost(currentUserId, postId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/commentSectionStatus/{postId}")
    public PostDTO updateCommentSectionStatus(@PathVariable("currentUserId") int currentUserId,
                                              @PathVariable("postId") int postId) {

        return forumService.updateCommentSectionStatus(currentUserId, postId);
    }

    @PatchMapping("/body/{postId}")
    public PostDTO updatePostBody(@PathVariable("currentUserId") int currentUserId,
                                  @PathVariable("postId") int postId,
                                  @RequestParam("newPostBody") String newPostBody) {

        return forumService.updatePostBody(currentUserId, postId, newPostBody);
    }

    @PatchMapping("/like/{postId}")
    public Optional<PostNotification> likePost(@PathVariable("currentUserId") int respondentId,
                                               @PathVariable("postId") int postId) {

        return forumService.likePost(respondentId, postId);
    }
}
