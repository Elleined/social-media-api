package com.elleined.forumapi.controller.comment;

import com.elleined.forumapi.dto.CommentDTO;
import com.elleined.forumapi.mapper.CommentMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.CommentService;
import com.elleined.forumapi.service.ModalTrackerService;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.notification.comment.reader.CommentMentionNotificationReader;
import com.elleined.forumapi.service.notification.comment.reader.CommentNotificationReader;
import com.elleined.forumapi.service.notification.comment.reader.CommentReactNotificationReader;
import com.elleined.forumapi.service.post.PostService;
import com.elleined.forumapi.service.ws.WSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/posts/{postId}/comments")
public class CommentController {
    private final UserService userService;

    private final PostService postService;

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    private final WSService wsService;

    private final CommentReactNotificationReader commentReactNotificationReader;
    private final CommentMentionNotificationReader commentMentionNotificationReader;
    private final CommentNotificationReader commentNotificationReader;

    private final ModalTrackerService modalTrackerService;

    @GetMapping
    public List<CommentDTO> getAllByPost(@PathVariable("currentUserId") int currentUserId,
                                         @PathVariable("postId") int postId) {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        commentNotificationReader.readAll(currentUser, post);
        commentReactNotificationReader.readAll(currentUser, post);
        commentMentionNotificationReader.readAll(currentUser, post);

        modalTrackerService.saveTrackerOfUserById(currentUserId, postId, "COMMENT");
        return commentService.getAllByPost(currentUser, post).stream()
                .map(commentMapper::toDTO)
                .toList();
    }

    @PostMapping
    public CommentDTO save(@PathVariable("currentUserId") int currentUserId,
                                  @PathVariable("postId") int postId,
                                  @RequestParam("body") String body,
                                  @RequestPart(required = false, value = "attachedPicture") MultipartFile attachedPicture,
                                  @RequestParam(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds) throws IOException {

        User currentUser = userService.getById(currentUserId);
        Set<User> mentionedUsers = userService.getAllById(mentionedUserIds);
        Post post = postService.getById(postId);

        Comment comment = commentService.save(currentUser, post, body, attachedPicture, mentionedUsers);
        wsService.broadcast(comment);
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
        wsService.broadcast(comment);
        return commentMapper.toDTO(comment);
    }

    @PatchMapping("/{commentId}/body")
    public CommentDTO updateBody(@PathVariable("currentUserId") int currentUserId,
                                        @PathVariable("postId") int postId,
                                        @PathVariable("commentId") int commentId,
                                        @RequestParam("newCommentBody") String newCommentBody) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        Comment updatedComment = commentService.updateBody(currentUser, post, comment, newCommentBody);
        wsService.broadcast(updatedComment);

        return commentMapper.toDTO(updatedComment);
    }
}
