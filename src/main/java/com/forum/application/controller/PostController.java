package com.forum.application.controller;

import com.forum.application.dto.PostDTO;
import com.forum.application.model.like.Like;
import com.forum.application.service.ForumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/{postId}")
    public PostDTO getById(@PathVariable("postId") int postId) {
        return forumService.getPostById(postId);
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
    public ResponseEntity<PostDTO> deletePost(@PathVariable("postId") int postId) {
        forumService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/commentSectionStatus/{postId}")
    public PostDTO updateCommentSectionStatus(@PathVariable("postId") int postId) {
        return forumService.updateCommentSectionStatus(postId);
    }

    @PatchMapping("/body/{postId}")
    public PostDTO updatePostBody(@PathVariable("postId") int postId,
                                                  @RequestParam("newPostBody") String newPostBody) {

        return forumService.updatePostBody(postId, newPostBody);
    }

    @PatchMapping("/like/{postId}")
    public Like likePost(@PathVariable("currentUserId") int respondentId,
                         @PathVariable("postId") int postId) {

        return forumService.likePost(respondentId, postId);
    }
}
