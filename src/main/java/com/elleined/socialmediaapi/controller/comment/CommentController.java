package com.elleined.socialmediaapi.controller.comment;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.dto.notification.main.CommentNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.CommentMentionNotificationDTO;
import com.elleined.socialmediaapi.mapper.main.CommentMapper;
import com.elleined.socialmediaapi.mapper.notification.main.CommentNotificationMapper;
import com.elleined.socialmediaapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.notification.main.CommentNotification;
import com.elleined.socialmediaapi.model.notification.mention.CommentMentionNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.hashtag.HashTagService;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.main.reply.ReplyService;
import com.elleined.socialmediaapi.service.mention.MentionService;
import com.elleined.socialmediaapi.service.notification.main.comment.CommentNotificationService;
import com.elleined.socialmediaapi.service.notification.mention.MentionNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
import com.elleined.socialmediaapi.ws.WSService;
import com.elleined.socialmediaapi.ws.notification.NotificationWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/posts/{postId}/comments")
public class CommentController {
    private final UserService userService;

    private final PostService postService;

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    private final ReplyService replyService;

    private final MentionService mentionService;
    private final HashTagService hashTagService;

    private final WSService wsService;
    private final NotificationWSService notificationWSService;

    private final CommentNotificationService commentNotificationService;
    private final CommentNotificationMapper commentNotificationMapper;

    private final MentionNotificationService<CommentMentionNotification, Comment> mentionNotificationService;
    private final MentionNotificationMapper mentionNotificationMapper;

    @GetMapping
    public Page<CommentDTO> getAll(@RequestHeader("Authorization") String jwt,
                                   @PathVariable("postId") int postId,
                                   @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                   @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                   @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                   @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return commentService.getAll(currentUser, post, pageable)
                .map(commentMapper::toDTO);
    }

    @GetMapping("/{id}")
    public CommentDTO getById(@PathVariable("id") int id) {
        Comment comment = commentService.getById(id);
        return commentMapper.toDTO(comment);
    }

    @PostMapping
    public CommentDTO save(@RequestHeader("Authorization") String jwt,
                           @PathVariable("postId") int postId,
                           @RequestPart("body") String body,
                           @RequestPart(required = false, name = "attachedPictures") List<MultipartFile> attachedPictures,
                           @RequestPart(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds,
                           @RequestPart(required = false, name = "hashTagIds") Set<Integer> hashTagIds) {

        // Getting entities
        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);
        Set<HashTag> hashTags = hashTagService.getAllById(hashTagIds);

        // Saving entities
        Set<Mention> mentions = mentionService.saveAll(currentUser, userService.getAllById(mentionedUserIds));
        Comment comment = commentService.save(currentUser, post, body, attachedPictures, mentions, hashTags);
        CommentNotification commentNotification = commentNotificationService.save(currentUser, comment);
        List<CommentMentionNotification> commentMentionNotifications = mentionNotificationService.saveAll(currentUser, mentions, comment);

        // DTO Conversion
        CommentDTO commentDTO = commentMapper.toDTO(comment);
        CommentNotificationDTO commentNotificationDTO = commentNotificationMapper.toDTO(commentNotification);
        List<CommentMentionNotificationDTO> commentMentionNotificationDTOS = commentMentionNotifications.stream()
                .map(mentionNotificationMapper::toDTO)
                .toList();

        // Web Socket
        wsService.broadcastOnComment(commentDTO);
        notificationWSService.notifyOnComment(commentNotificationDTO);
        commentMentionNotificationDTOS.forEach(notificationWSService::notifyOnMentioned);

        return commentDTO;
    }

    @DeleteMapping("/{commentId}")
    public void delete(@RequestHeader("Authorization") String jwt,
                       @PathVariable("postId") int postId,
                       @PathVariable("commentId") int commentId) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        commentService.delete(currentUser, post, comment);
        comment.getReplies().forEach(reply -> replyService.delete(currentUser, post, comment, reply));

        CommentDTO commentDTO = commentMapper.toDTO(comment);
        wsService.broadcastOnComment(commentDTO);
    }

    @PutMapping("/{commentId}")
    public CommentDTO update(@RequestHeader("Authorization") String jwt,
                             @PathVariable("postId") int postId,
                             @PathVariable("commentId") int commentId,
                             @RequestPart("newBody") String newBody,
                             @RequestPart(required = false, name = "attachedPictures") List<MultipartFile> attachedPictures) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        commentService.update(currentUser, post, comment, newBody, attachedPictures);

        CommentDTO commentDTO = commentMapper.toDTO(comment);
        wsService.broadcastOnComment(commentDTO);

        return commentDTO;
    }

    @PatchMapping("/{commentId}/reactivate")
    public CommentDTO reactivate(@RequestHeader("Authorization") String jwt,
                                 @PathVariable("postId") int postId,
                                 @PathVariable("commentId") int commentId) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        commentService.reactivate(currentUser, post, comment);
        comment.getReplies().forEach(reply -> replyService.reactivate(currentUser, post, comment, reply));
        return commentMapper.toDTO(comment);
    }
}
