package com.elleined.forumapi.controller;

import com.elleined.forumapi.dto.notification.CommentNotification;
import com.elleined.forumapi.dto.notification.Notification;
import com.elleined.forumapi.dto.notification.PostNotification;
import com.elleined.forumapi.dto.notification.ReplyNotification;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.NotificationService;
import com.elleined.forumapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/{currentUserId}/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow other ports to access these endpoints
public class NotificationController {

    private final UserService userService;
    private final NotificationService notificationService;


    @GetMapping("/getAllNotification")
    public Set<Notification> getAllNotification(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return notificationService.getAllNotification(currentUser);
    }

    @GetMapping("/getTotalNotificationCount")
    public long getTotalNotificationCount(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        return notificationService.getTotalNotificationCount(currentUser);
    }

//    @GetMapping("/getCommentNotification/{commentId}")
//    public CommentNotification getCommentNotification(@PathVariable("commentId") int commentId) {
//        return notificationService.getCommentNotification(commentId);
//    }
//
//    @GetMapping("/getReplyNotification/{replyId}")
//    public ReplyNotification getReplyNotification(@PathVariable("replyId") int replyId) {
//        return notificationService.getReplyNotification(replyId);
//    }
//
//    @GetMapping("/getPostMentionsNotification/{postId}")
//    public Set<PostNotification> getPostMentionsNotification(@PathVariable("currentUserId") int currentUserId,
//                                                             @PathVariable("postId") int postId) {
//        return notificationService.getPostMentionsNotification(currentUserId, postId);
//    }
//
//
//    @GetMapping("/getCommentMentionsNotification/{commentId}")
//    public Set<CommentNotification> getCommentMentionsNotification(@PathVariable("currentUserId") int currentUserId,
//                                                                   @PathVariable("commentId") int commentId) {
//        return notificationService.getCommentMentionsNotification(currentUserId, commentId);
//    }
//
//    @GetMapping("/getReplyMentionsNotification/{replyId}")
//    public Set<ReplyNotification> getReplyMentionsNotification(@PathVariable("currentUserId") int currentUserId,
//                                                               @PathVariable("replyId") int replyId) {
//        return notificationService.getReplyMentionsNotification(currentUserId, replyId);
//    }
//
//    @GetMapping("/getPostLikeNotification/{postId}")
//    public Set<PostNotification> getPostLikeNotification(@PathVariable("currentUserId") int currentUserId,
//                                                         @PathVariable("postId") int postId) {
//        return notificationService.getPostLikeNotification(currentUserId, postId);
//    }
//
//    @GetMapping("/getCommentLikeNotification/{commentId}")
//    public Set<CommentNotification> getCommentLikeNotification(@PathVariable("currentUserId") int currentUserId,
//                                                                    @PathVariable("commentId") int commentId) {
//
//        return notificationService.getCommentLikeNotification(currentUserId, commentId);
//    }
//
//    @GetMapping("/getReplyLikeNotification/{replyId}")
//    public Set<ReplyNotification> getReplyLikeNotification(@PathVariable("currentUserId") int currentUserId,
//                                                                @PathVariable("replyId") int replyId) {
//        return notificationService.getReplyLikeNotification(currentUserId, replyId);
//    }
}
