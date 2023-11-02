package com.elleined.forumapi.controller;

import com.elleined.forumapi.dto.CommentDTO;
import com.elleined.forumapi.dto.ReplyDTO;
import com.elleined.forumapi.mapper.CommentMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.*;
import com.elleined.forumapi.service.notification.reader.comment.CommentLikeNotificationReader;
import com.elleined.forumapi.service.notification.reader.comment.CommentMentionNotificationReader;
import com.elleined.forumapi.service.notification.reader.comment.CommentNotificationReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/posts/{postId}/comments")
public class CommentController {
    private final UserService userService;

    private final PostService postService;

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    private final WSNotificationService wsNotificationService;
    private final WSService wsService;

    private final CommentLikeNotificationReader commentLikeNotificationReader;
    private final CommentMentionNotificationReader commentMentionNotificationReader;
    private final CommentNotificationReader commentNotificationReader;

    private final ModalTrackerService modalTrackerService;

    @GetMapping
    public List<CommentDTO> getAllByPost(@PathVariable("currentUserId") int currentUserId,
                                         @PathVariable("postId") int postId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        commentNotificationReader.readAll(currentUser, post);
        commentLikeNotificationReader.readAll(currentUser, post);
        commentMentionNotificationReader.readAll(currentUser, post);

        modalTrackerService.saveTrackerOfUserById(currentUserId, postId, "COMMENT");
        return commentService.getAllByPost(currentUser, post).stream()
                .map(commentMapper::toDTO)
                .toList();
    }

    @GetMapping("/getPinnedReply/{commentId}")
    public Optional<ReplyDTO> getPinnedReply(@PathVariable("commentId") int commentId) {
        return forumService.getPinnedReply(commentId);
    }

    @PostMapping
    public CommentDTO saveComment(@PathVariable("currentUserId") int currentUserId,
                                  @PathVariable("postId") int postId,
                                  @RequestParam("body") String body,
                                  @RequestPart(required = false, value = "attachedPicture") MultipartFile attachedPicture,
                                  @RequestParam(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds) throws IOException {

        User currentUser = userService.getById(currentUserId);
        Set<User> mentionedUsers = userService.getAllById(mentionedUserIds);
        Post post = postService.getById(postId);

        Comment comment = commentService.save(currentUser, post, body, attachedPicture, mentionedUsers);
        if (mentionedUsers != null) wsNotificationService.broadcastCommentMentions(comment.getMentions());
        wsNotificationService.broadcastCommentNotification(comment);
        wsService.broadcastComment(comment);

        return commentMapper.toDTO(comment);
    }

    @DeleteMapping("/{commentId}")
    public CommentDTO delete(@PathVariable("currentUserId") int currentUserId,
                             @PathVariable("postId") int postId,
                             @PathVariable("commentId") int commentId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        commentService.delete(currentUser, post, comment);
        wsService.broadcastComment(comment);
        return commentMapper.toDTO(comment);
    }

    @PatchMapping("/upvote/{commentId}")
    public CommentDTO updateCommentUpvote(@PathVariable("currentUserId") int currentUserId,
                                          @PathVariable("commentId") int commentId) {

        return forumService.updateUpvote(currentUserId, commentId);
    }

    @PatchMapping("/body/{commentId}")
    public CommentDTO updateCommentBody(@PathVariable("currentUserId") int currentUserId,
                                        @PathVariable("postId") int postId,
                                        @PathVariable("commentId") int commentId,
                                        @RequestParam("newCommentBody") String newCommentBody) {

        return forumService.updateCommentBody(currentUserId, postId, commentId, newCommentBody);
    }

    @PatchMapping("/like/{commentId}")
    public CommentDTO likeComment(@PathVariable("currentUserId") int respondentId,
                                  @PathVariable("postId") int postId,
                                  @PathVariable("commentId") int commentId) {

        return forumService.likeComment(respondentId, postId, commentId);
    }

    @PatchMapping("/{commentId}/pinReply/{replyId}")
    public CommentDTO pinReply(@PathVariable("currentUserId") int currentUserId,
                               @PathVariable("commentId") int commentId,
                               @PathVariable("replyId") int replyId) {

        return forumService.pinReply(currentUserId, commentId, replyId);
    }
}
