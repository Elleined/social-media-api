package com.forum.application.controller;

import com.forum.application.dto.CommentDTO;
import com.forum.application.service.ForumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/posts/{postId}/comments")
public class CommentController {

    private final ForumService forumService;

    @GetMapping
    public List<CommentDTO> getAllByPost(@PathVariable("currentUserId") int currentUserId,
                                             @PathVariable("postId") int postId) {
        return forumService.getAllByPost(currentUserId, postId);
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
    public CommentDTO delete(@PathVariable("currentUserId") int currentUserId,
                             @PathVariable("commentId") int commentId) {

        return forumService.deleteComment(currentUserId, commentId);
    }

    @PatchMapping("/upvote/{commentId}")
    public CommentDTO updateCommentUpvote(@PathVariable("currentUserId") int currentUserId,
                                          @PathVariable("commentId") int commentId) {

        return forumService.updateUpvote(currentUserId, commentId);
    }

    @PatchMapping("/body/{commentId}")
    public CommentDTO updateCommentBody(@PathVariable("currentUserId") int currentUserId,
                                        @PathVariable("commentId") int commentId,
                                        @RequestParam("newCommentBody") String newCommentBody) {

        return forumService.updateCommentBody(currentUserId, commentId, newCommentBody);
    }

    @PatchMapping("/like/{commentId}")
    public CommentDTO likeComment(@PathVariable("currentUserId") int respondentId,
                                  @PathVariable("commentId") int commentId) {

        return forumService.likeComment(respondentId, commentId);
    }

    @PatchMapping("/{commentId}/pinReply/{replyId}")
    public CommentDTO pinReply(@PathVariable("currentUserId") int currentUserId,
                               @PathVariable("commentId") int commentId,
                               @PathVariable("replyId") int replyId) {

        return forumService.pinReply(currentUserId, commentId, replyId);
    }
}
