package com.elleined.forumapi.controller;

import com.elleined.forumapi.dto.ReplyDTO;
import com.elleined.forumapi.service.ForumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/posts/comments/{commentId}/replies")
public class ReplyController {

    private final ForumService forumService;

    @GetMapping
    public List<ReplyDTO> getAllByComment(@PathVariable("currentUserId") int currentUserId,
                                          @PathVariable("commentId") int commentId) {

        return forumService.getAllByComment(currentUserId, commentId);
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
    public ReplyDTO delete(@PathVariable("currentUserId") int currentUserId,
                           @PathVariable("commentId") int commentId,
                           @PathVariable("replyId") int replyId) {

        return forumService.deleteReply(currentUserId, commentId, replyId);
    }

    @PatchMapping("/body/{replyId}")
    public ReplyDTO updateReplyBody(@PathVariable("currentUserId") int currentUserId,
                                    @PathVariable("replyId") int replyId,
                                    @RequestParam("newReplyBody") String newReplyBody) {

        return forumService.updateReplyBody(currentUserId, replyId, newReplyBody);
    }

    @PatchMapping("/like/{replyId}")
    public ReplyDTO likeReply(@PathVariable("currentUserId") int respondentId,
                              @PathVariable("replyId") int replyId) {

        return forumService.likeReply(respondentId, replyId);
    }
}
