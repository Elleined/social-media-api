package com.forum.application.controller;

import com.forum.application.dto.ReplyDTO;
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
@RequestMapping("/{currentUserId}/posts/comments/{commentId}/replies")
public class ReplyController {

    private final ForumService forumService;

    @GetMapping
    public List<ReplyDTO> getAllRepliesOf(@PathVariable("currentUserId") int currentUserId,
                                          @PathVariable("commentId") int commentId) {

        return forumService.getAllRepliesOf(currentUserId, commentId);
    }

    @GetMapping("/{replyId}")
    public ReplyDTO getById(@PathVariable("replyId") int replyId) {
        return forumService.getReplyById(replyId);
    }

    @PostMapping
    public ReplyDTO saveReply(@PathVariable("currentUserId") int currentUserId,
                              @PathVariable("commentId") int commentId,
                              @RequestParam("body") String body,
                              @RequestParam(required = false, name = "attachedPicture") String attachedPicture,
                              @RequestParam(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds) {

        return forumService.saveReply(currentUserId, commentId, body, attachedPicture, mentionedUserIds);
    }

    @DeleteMapping("/{replyId}")
    public ReplyDTO delete(@PathVariable("replyId") int replyId) {
        return forumService.deleteReply(replyId);
    }

    @PatchMapping("/body/{replyId}")
    public ReplyDTO updateReplyBody(@PathVariable("replyId") int replyId,
                                    @RequestParam("newReplyBody") String newReplyBody) {

        return forumService.updateReplyBody(replyId, newReplyBody);
    }

    @PatchMapping("/like/{replyId}")
    public Optional<Like> likeReply(@PathVariable("currentUserId") int respondentId,
                                    @PathVariable("replyId") int replyId) {

        return forumService.likeReply(respondentId, replyId);
    }
}
