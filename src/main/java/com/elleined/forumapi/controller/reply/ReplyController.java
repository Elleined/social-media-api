package com.elleined.forumapi.controller.reply;

import com.elleined.forumapi.dto.ReplyDTO;
import com.elleined.forumapi.mapper.ReplyMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.ReplyLike;
import com.elleined.forumapi.service.CommentService;
import com.elleined.forumapi.service.ModalTrackerService;
import com.elleined.forumapi.service.ReplyService;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.notification.reader.reply.ReplyLikeNotificationReader;
import com.elleined.forumapi.service.notification.reader.reply.ReplyMentionNotificationReader;
import com.elleined.forumapi.service.notification.reader.reply.ReplyNotificationReader;
import com.elleined.forumapi.service.ws.WSService;
import com.elleined.forumapi.service.ws.notification.reply.ReplyWSNotificationService;
import com.elleined.forumapi.service.ws.notification.like.ReplyWSLikeNotificationService;
import com.elleined.forumapi.service.ws.notification.mention.ReplyWSMentionNotificationService;
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

    private final ModalTrackerService modalTrackerService;

    private final ReplyWSNotificationService replyWSNotificationService;

    private final ReplyWSMentionNotificationService replyMentionWSNotificationService;

    private final WSService wsService;

    private final ReplyLikeNotificationReader replyLikeNotificationReader;
    private final ReplyMentionNotificationReader replyMentionNotificationReader;
    private final ReplyNotificationReader replyNotificationReader;


    @GetMapping
    public List<ReplyDTO> getAllByComment(@PathVariable("currentUserId") int currentUserId,
                                          @PathVariable("commentId") int commentId) {
        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);

        replyNotificationReader.readAll(currentUser, comment);
        replyLikeNotificationReader.readAll(currentUser, comment);
        replyMentionNotificationReader.readAll(currentUser, comment);

        modalTrackerService.saveTrackerOfUserById(currentUserId, commentId, "REPLY");
        return replyService.getAllByComment(currentUser, comment).stream()
                .map(replyMapper::toDTO)
                .toList();
    }

    @PostMapping
    public ReplyDTO save(@PathVariable("currentUserId") int currentUserId,
                              @PathVariable("commentId") int commentId,
                              @RequestParam("body") String body,
                              @RequestPart(required = false, name = "attachedPicture") MultipartFile attachedPicture,
                              @RequestParam(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds) throws IOException {

        User currentUser = userService.getById(currentUserId);
        Set<User> mentionedUsers = userService.getAllById(mentionedUserIds);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.save(currentUser, comment, body, attachedPicture, mentionedUsers);

        if (mentionedUsers != null) replyMentionWSNotificationService.broadcastMentions(reply.getMentions());

        replyWSNotificationService.broadcast(reply);
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
    public ReplyDTO updateBody(@PathVariable("currentUserId") int currentUserId,
                               @PathVariable("replyId") int replyId,
                               @RequestParam("newReplyBody") String newReplyBody) {

        User currentUser = userService.getById(currentUserId);
        Reply reply = replyService.getById(replyId);

        Reply updatedReply = replyService.updateBody(currentUser, reply, newReplyBody);
        wsService.broadcastReply(updatedReply);
        return replyMapper.toDTO(updatedReply);
    }
}
