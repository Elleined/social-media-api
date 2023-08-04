package com.forum.application.controller;

import com.forum.application.dto.CommentDTO;
import com.forum.application.dto.PostDTO;
import com.forum.application.dto.ReplyDTO;
import com.forum.application.dto.notification.Notification;
import com.forum.application.model.User;
import com.forum.application.service.LikeService;
import com.forum.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{currentUserId}/views")
public class DynamicViewController {

    private final UserService userService;
    private final LikeService likeService;

    @PostMapping("/getPostBlock")
    public ModelAndView getPostBlock(@PathVariable("currentUserId") int currentUserId,
                                     @RequestBody PostDTO postDto) {

        User currentUser = userService.getById(currentUserId);
        boolean currentUserLikedPost = likeService.isUserAlreadyLiked(currentUser, postDto);
        return new ModelAndView("post-body")
                .addObject("currentUserLikedPost", currentUserLikedPost)
                .addObject("currentUserId", currentUser.getId())
                .addObject("post", postDto);
    }

    @PostMapping("/getCommentBlock")
    public ModelAndView getCommentBlock(@PathVariable("currentUserId") int currentUserId,
                                        @RequestBody CommentDTO commentDto) {

        User currentUser = userService.getById(currentUserId);
        boolean currentUserLikedComment = likeService.isUserAlreadyLiked(currentUser, commentDto);
        return new ModelAndView("comment-body")
                .addObject("currentUserLikedComment", currentUserLikedComment)
                .addObject("currentUserId", currentUser.getId())
                .addObject("commentDto", commentDto);
    }

    @PostMapping("/getReplyBlock")
    public ModelAndView getReplyBlock(@PathVariable("currentUserId") int currentUserId,
                                      @RequestBody ReplyDTO replyDto) {

        User currentUser = userService.getById(currentUserId);
        boolean currentUserLikedReply = likeService.isUserAlreadyLiked(currentUser, replyDto);
        return new ModelAndView("reply-body")
                .addObject("currentUserLikedReply", currentUserLikedReply)
                .addObject("currentUserId", currentUser.getId())
                .addObject("replyDto", replyDto);
    }

    @PostMapping("/getNotificationBlock")
    public ModelAndView getNotificationBlock(@PathVariable("currentUserId") int currentUserId,
                                             @RequestBody Notification notification) {

        User currentUser = userService.getById(currentUserId);
        return new ModelAndView("notification-body")
                .addObject("currentUserId", currentUser.getId())
                .addObject("notification", notification);
    }

    @PostMapping("/getLikeIcon")
    public ModelAndView getLikeIcon(@RequestParam("isLiked") boolean isLiked) {
        return new ModelAndView("like-icon").addObject("isLiked", isLiked);
    }
}
