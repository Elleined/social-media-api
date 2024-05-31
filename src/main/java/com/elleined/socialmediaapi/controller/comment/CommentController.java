package com.elleined.socialmediaapi.controller.comment;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.dto.notification.main.CommentNotificationDTO;
import com.elleined.socialmediaapi.mapper.main.CommentMapper;
import com.elleined.socialmediaapi.mapper.notification.CommentNotificationMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.notification.main.CommentNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.hashtag.HashTagService;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.main.reply.ReplyService;
import com.elleined.socialmediaapi.service.notification.main.comment.CommentNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
import com.elleined.socialmediaapi.service.ws.WSService;
import com.elleined.socialmediaapi.service.ws.notification.NotificationWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
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

    private final ReplyService replyService;

    private final HashTagService hashTagService;

    private final WSService wsService;
    private final NotificationWSService notificationWSService;

    private final CommentNotificationService commentNotificationService;
    private final CommentNotificationMapper commentNotificationMapper;

    @GetMapping
    public List<CommentDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                   @PathVariable("postId") int postId,
                                   @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                   @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                   @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                   @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return commentService.getAll(currentUser, post, pageable).stream()
                .map(commentMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public CommentDTO getById(@PathVariable("id") int id) {
        Comment comment = commentService.getById(id);
        return commentMapper.toDTO(comment);
    }

    @GetMapping("/get-all-by-id")
    public List<CommentDTO> getAllById(@RequestBody List<Integer> ids) {
        return commentService.getAllById(ids).stream()
                .map(commentMapper::toDTO)
                .toList();
    }

    @PostMapping
    public CommentDTO save(@PathVariable("currentUserId") int currentUserId,
                           @PathVariable("postId") int postId,
                           @RequestPart("body") String body,
                           @RequestPart(required = false, value = "attachedPicture") MultipartFile attachedPicture,
                           @RequestPart(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds,
                           @RequestPart(required = false, name = "hashTagIds") Set<Integer> hashTagIds) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Set<User> mentionedUsers = new HashSet<>(userService.getAllById(mentionedUserIds.stream().toList()));
        Set<HashTag> hashTags = new HashSet<>(hashTagService.getAllById(hashTagIds.stream().toList()));

        Comment comment = commentService.save(currentUser, post, body, attachedPicture, mentionedUsers, hashTags);
        CommentNotification commentNotification = commentNotificationService.save(currentUser, comment);

        CommentDTO commentDTO = commentMapper.toDTO(comment);
        CommentNotificationDTO commentNotificationDTO = commentNotificationMapper.toDTO(commentNotification);

        wsService.broadcastOnComment(commentDTO);
        notificationWSService.notifyOnComment(commentNotificationDTO);
        return commentDTO;
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable("currentUserId") int currentUserId,
                       @PathVariable("postId") int postId,
                       @PathVariable("commentId") int commentId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        commentService.delete(currentUser, post, comment);
        comment.getReplies().forEach(reply -> replyService.delete(currentUser, post, comment, reply));

        CommentDTO commentDTO = commentMapper.toDTO(comment);
        wsService.broadcastOnComment(commentDTO);
    }

    @PutMapping("/{commentId}")
    public CommentDTO update(@PathVariable("currentUserId") int currentUserId,
                             @PathVariable("postId") int postId,
                             @PathVariable("commentId") int commentId,
                             @RequestPart("newBody") String newBody,
                             @RequestPart(required = false, name = "newAttachedPicture") MultipartFile newAttachedPicture) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        commentService.update(currentUser, post, comment, newBody, newAttachedPicture);

        CommentDTO commentDTO = commentMapper.toDTO(comment);
        wsService.broadcastOnComment(commentDTO);

        return commentDTO;
    }

    @PatchMapping("/{commentId}/reactivate")
    public CommentDTO reactivate(@PathVariable("currentUserId") int currentUserId,
                                 @PathVariable("postId") int postId,
                                 @PathVariable("commentId") int commentId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        commentService.reactivate(currentUser, post, comment);
        comment.getReplies().forEach(reply -> replyService.reactivate(currentUser, post, comment, reply));
        return commentMapper.toDTO(comment);
    }
}
