package com.elleined.socialmediaapi.controller.reply;

import com.elleined.socialmediaapi.dto.ReplyDTO;
import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.mapper.ReplyMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.reply.ReplyService;
import com.elleined.socialmediaapi.service.mt.ModalTrackerService;
import com.elleined.socialmediaapi.service.notification.reply.reader.ReplyMentionNotificationReader;
import com.elleined.socialmediaapi.service.notification.reply.reader.ReplyNotificationReader;
import com.elleined.socialmediaapi.service.notification.reply.reader.ReplyReactNotificationReader;
import com.elleined.socialmediaapi.service.user.UserService;
import com.elleined.socialmediaapi.service.ws.WSService;
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

        modalTrackerService.saveTrackerByUserId(currentUserId, commentId, Type.REPLY);
        return replyService.getAllByComment(currentUser, comment).stream()
                .map(replyMapper::toDTO)
                .toList();
    }

    @GetMapping("/get-all-by-id")
    public List<UserDTO> getAllById(@RequestBody List<Integer> ids) {
        return userService.getAllById(ids).stream()
                .map(userMapper::toDTO)
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
