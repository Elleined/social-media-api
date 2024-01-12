package com.elleined.forumapi.controller.reply;

import com.elleined.forumapi.dto.ReplyDTO;
import com.elleined.forumapi.mapper.ReplyMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.CommentService;
import com.elleined.forumapi.service.ModalTrackerService;
import com.elleined.forumapi.service.ReplyService;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.notification.reply.reader.ReplyMentionNotificationReader;
import com.elleined.forumapi.service.notification.reply.reader.ReplyNotificationReader;
import com.elleined.forumapi.service.notification.reply.reader.ReplyReactNotificationReader;
import com.elleined.forumapi.service.ws.WSService;
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
@RequestMapping("/users/{currentUserId}/posts/comments/{commentId}/replies")
public class ReplyController {

    private final UserService userService;
    private final CommentService commentService;

    private final ReplyService replyService;
    private final ReplyMapper replyMapper;

    private final ModalTrackerService modalTrackerService;

    private final WSService wsService;

    private final ReplyReactNotificationReader replyReactNotificationReader;
    private final ReplyMentionNotificationReader replyMentionNotificationReader;
    private final ReplyNotificationReader replyNotificationReader;


    @GetMapping
    public List<ReplyDTO> getAllByComment(@PathVariable("currentUserId") int currentUserId,
                                          @PathVariable("commentId") int commentId) {
        User currentUser = userService.getById(currentUserId);
        Comment comment = commentService.getById(commentId);

        replyNotificationReader.readAll(currentUser, comment);
        replyReactNotificationReader.readAll(currentUser, comment);
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
                         @RequestParam(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds,
                         @RequestParam(required = false, name = "keywords") Set<String> keywords) throws IOException {

        User currentUser = userService.getById(currentUserId);
        Set<User> mentionedUsers = userService.getAllById(mentionedUserIds);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.save(currentUser, comment, body, attachedPicture, mentionedUsers, keywords);
        wsService.broadcast(reply);
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
        wsService.broadcast(reply);
        return replyMapper.toDTO(reply);
    }

    @PatchMapping("/{replyId}/body")
    public ReplyDTO updateBody(@PathVariable("currentUserId") int currentUserId,
                               @PathVariable("replyId") int replyId,
                               @RequestParam("newReplyBody") String newReplyBody) {

        User currentUser = userService.getById(currentUserId);
        Reply reply = replyService.getById(replyId);

        Reply updatedReply = replyService.updateBody(currentUser, reply, newReplyBody);
        wsService.broadcast(updatedReply);
        return replyMapper.toDTO(updatedReply);
    }
}
