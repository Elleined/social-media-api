package com.forum.application.controller;

import com.forum.application.dto.CommentDTO;
import com.forum.application.model.like.CommentLike;
import com.forum.application.model.like.Like;
import com.forum.application.service.ForumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/posts/{postId}/comments")
public class CommentController {

    private final ForumService forumService;

    @GetMapping
    public List<CommentDTO> getAllCommentsOf(@PathVariable("currentUserId") int currentUserId,
                                             @PathVariable("postId") int postId) {
        return forumService.getAllCommentsOf(currentUserId, postId);
    }

    @GetMapping("/{commentId}")
    public CommentDTO getById(@PathVariable("commentId") int commentId) {
        return forumService.getCommentById(commentId);
    }

    @PostMapping
    public CommentDTO saveComment(@PathVariable("currentUserId") int currentUserId,
                                  @PathVariable("postId") int postId,
                                  @RequestParam("body") String body,
                                  @RequestParam(required = false, value = "attachedPicture") String attachedPicture,
                                  @RequestParam(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds) {

        return forumService.saveComment(currentUserId, postId, body, attachedPicture, mentionedUserIds);
    }

    @DeleteMapping("/{commentId}")
    public CommentDTO delete(@PathVariable("commentId") int commentId) {
        return forumService.deleteComment(commentId);
    }

    @PatchMapping("/upvote/{commentId}")
    public CommentDTO updateCommentUpvote(@PathVariable("currentUserId") int currentUserId,
                                          @PathVariable("commentId") int commentId) {

        return forumService.updateUpvote(currentUserId, commentId);
    }

    @PatchMapping("/body/{commentId}")
    public CommentDTO updateCommentBody(@PathVariable("commentId") int commentId,
                                        @RequestParam("newCommentBody") String newCommentBody) {

        return forumService.updateCommentBody(commentId, newCommentBody);
    }

    @PatchMapping("/like/{commentId}")
    public Like likeComment(@PathVariable("currentUserId") int respondentId,
                            @PathVariable("commentId") int commentId) {

        return forumService.likeComment(respondentId, commentId);
    }
}
