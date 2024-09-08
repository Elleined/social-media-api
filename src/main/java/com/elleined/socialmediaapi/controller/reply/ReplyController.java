package com.elleined.socialmediaapi.controller.reply;

import com.elleined.socialmediaapi.dto.main.ReplyDTO;
import com.elleined.socialmediaapi.dto.notification.main.ReplyNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.ReplyMentionNotificationDTO;
import com.elleined.socialmediaapi.mapper.main.ReplyMapper;
import com.elleined.socialmediaapi.mapper.notification.main.ReplyNotificationMapper;
import com.elleined.socialmediaapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.notification.main.ReplyNotification;
import com.elleined.socialmediaapi.model.notification.mention.ReplyMentionNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.hashtag.HashTagService;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.main.reply.ReplyService;
import com.elleined.socialmediaapi.service.mention.MentionService;
import com.elleined.socialmediaapi.service.notification.main.reply.ReplyNotificationService;
import com.elleined.socialmediaapi.service.notification.mention.MentionNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
import com.elleined.socialmediaapi.ws.WSService;
import com.elleined.socialmediaapi.ws.notification.NotificationWSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/posts/{postId}/comments/{commentId}/replies")
public class ReplyController {

    private final UserService userService;

    private final PostService postService;

    private final CommentService commentService;

    private final ReplyService replyService;
    private final ReplyMapper replyMapper;

    private final HashTagService hashTagService;
    private final MentionService mentionService;

    private final WSService wsService;
    private final NotificationWSService notificationWSService;

    private final ReplyNotificationService replyNotificationService;
    private final ReplyNotificationMapper replyNotificationMapper;

    private final MentionNotificationService<ReplyMentionNotification, Reply> mentionNotificationService;
    private final MentionNotificationMapper mentionNotificationMapper;

    @GetMapping
    public Page<ReplyDTO> getAll(@RequestHeader("Authorization") String jwt,
                                 @PathVariable("postId") int postId,
                                 @PathVariable("commentId") int commentId,
                                 @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                 @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                 @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                 @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);
        return replyService.getAll(currentUser, post, comment, pageable)
                .map(replyMapper::toDTO);
    }

    @GetMapping("/{id}")
    public ReplyDTO getById(@PathVariable("id") int id) {
        Reply reply = replyService.getById(id);
        return replyMapper.toDTO(reply);
    }

    @PostMapping
    public ReplyDTO save(@RequestHeader("Authorization") String jwt,
                         @PathVariable("postId") int postId,
                         @PathVariable("commentId") int commentId,
                         @RequestParam("body") String body,
                         @RequestPart(required = false, name = "attachedPictures") List<MultipartFile> attachedPictures,
                         @RequestPart(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds,
                         @RequestPart(required = false, name = "hashTagIds") Set<Integer> hashTagIds) throws IOException {

        // Getting entities
        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);
        Set<HashTag> hashTags = hashTagService.getAllById(hashTagIds);

        // Saving entities
        Set<Mention> mentions = mentionService.saveAll(currentUser, userService.getAllById(mentionedUserIds));
        Reply reply = replyService.save(currentUser, post, comment, body, attachedPictures, mentions, hashTags);
        ReplyNotification replyNotification = replyNotificationService.save(currentUser, reply);
        List<ReplyMentionNotification> replyMentionNotifications = mentionNotificationService.saveAll(currentUser, mentions, reply);

        // DTO Conversion
        ReplyDTO replyDTO = replyMapper.toDTO(reply);
        ReplyNotificationDTO replyNotificationDTO = replyNotificationMapper.toDTO(replyNotification);
        List<ReplyMentionNotificationDTO> replyMentionNotificationDTOS = replyMentionNotifications.stream()
                .map(mentionNotificationMapper::toDTO)
                .toList();

        // Web Socket
        wsService.broadcastOnReply(replyDTO);
        notificationWSService.notifyOnReply(replyNotificationDTO);
        replyMentionNotificationDTOS.forEach(notificationWSService::notifyOnMentioned);

        return replyDTO;
    }

    @DeleteMapping("/{replyId}")
    public void delete(@RequestHeader("Authorization") String jwt,
                           @PathVariable("postId") int postId,
                           @PathVariable("commentId") int commentId,
                           @PathVariable("replyId") int replyId) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.getById(replyId);

        replyService.delete(currentUser, post, comment, reply);

        ReplyDTO replyDTO = replyMapper.toDTO(reply);
        wsService.broadcastOnReply(replyDTO);
    }

    @PutMapping("/{replyId}")
    public ReplyDTO update(@RequestHeader("Authorization") String jwt,
                           @PathVariable("postId") int postId,
                           @PathVariable("commentId") int commentId,
                           @PathVariable("replyId") int replyId,
                           @RequestPart("newBody") String newBody,
                           @RequestPart(required = false, name = "attachedPictures") List<MultipartFile> attachedPictures) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.getById(replyId);

        replyService.update(currentUser, post, comment, reply, newBody, attachedPictures);

        ReplyDTO replyDTO = replyMapper.toDTO(reply);
        wsService.broadcastOnReply(replyDTO);

        return replyDTO;
    }

    @PatchMapping("/{replyId}/reactivate")
    public ReplyDTO reactivate(@RequestHeader("Authorization") String jwt,
                               @PathVariable("postId") int postId,
                               @PathVariable("commentId") int commentId,
                               @PathVariable("replyId") int replyId) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.getById(replyId);

        replyService.reactivate(currentUser, post, comment, reply);
        return replyMapper.toDTO(reply);
    }
}
