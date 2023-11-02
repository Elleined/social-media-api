package com.elleined.forumapi.controller;

import com.elleined.forumapi.dto.ReplyDTO;
import com.elleined.forumapi.mapper.ReplyMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/posts/comments/{commentId}/replies")
public class ReplyController {

    private final UserService userService;
    private final CommentService commentService;

    private final ReplyService replyService;
    private final ReplyMapper replyMapper;

    private final WSNotificationService wsNotificationService;
    private final WSService wsService;


    @GetMapping
    public List<ReplyDTO> getAllByComment(@PathVariable("currentUserId") int currentUserId,
                                          @PathVariable("commentId") int commentId) {
        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);

        return replyService.getAllByComment(currentUser, comment).stream()
                .map(replyMapper::toDTO)
                .toList();
    }

    @PostMapping
    public ReplyDTO saveReply(@PathVariable("currentUserId") int currentUserId,
                              @PathVariable("commentId") int commentId,
                              @RequestParam("body") String body,
                              @RequestPart(required = false, name = "attachedPicture") MultipartFile attachedPicture,
                              @RequestParam(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds) throws IOException {

        User currentUser = userService.getById(currentUserId);
        Set<User> mentionedUsers = userService.getAllById(mentionedUserIds);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.save(currentUser, comment, body, attachedPicture, mentionedUsers);

        if (mentionedUsers != null) wsNotificationService.broadcastReplyMentions(reply.getMentions());
        wsNotificationService.broadcastReplyNotification(reply);
        wsService.broadcastReply(reply);

        return replyMapper.toDTO(reply);
    }

    @DeleteMapping("/{replyId}")
    public ReplyDTO delete(@PathVariable("currentUserId") int currentUserId,
                           @PathVariable("commentId") int commentId,
                           @PathVariable("replyId") int replyId) {

        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.getById(replyId);

        replyService.delete(currentUser, comment, reply);
        wsService.broadcastReply(reply);
        return replyMapper.toDTO(reply);
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
