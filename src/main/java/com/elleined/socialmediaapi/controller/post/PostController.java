package com.elleined.socialmediaapi.controller.post;

import com.elleined.socialmediaapi.dto.main.PostDTO;
import com.elleined.socialmediaapi.dto.notification.mention.PostMentionNotificationDTO;
import com.elleined.socialmediaapi.mapper.main.PostMapper;
import com.elleined.socialmediaapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.notification.mention.PostMentionNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.hashtag.HashTagService;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.mention.MentionService;
import com.elleined.socialmediaapi.service.notification.mention.MentionNotificationService;
import com.elleined.socialmediaapi.service.user.UserService;
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
@RequestMapping("/users/posts")
public class PostController {
    private final UserService userService;

    private final PostService postService;
    private final PostMapper postMapper;

    private final CommentService commentService;

    private final MentionService mentionService;
    private final HashTagService hashTagService;

    private final MentionNotificationService<PostMentionNotification, Post> mentionNotificationService;
    private final MentionNotificationMapper mentionNotificationMapper;

    private final NotificationWSService notificationWSService;

    @GetMapping("/{id}")
    public PostDTO getById(@PathVariable("id") int id) {
        Post post = postService.getById(id);
        return postMapper.toDTO(post);
    }

    @GetMapping
    public Page<PostDTO> getAll(@RequestHeader("Authorization") String jwt,
                                @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);
        return postService.getAll(currentUser, pageable)
                .map(postMapper::toDTO);
    }

    @PostMapping
    public PostDTO save(@RequestHeader("Authorization") String jwt,
                        @RequestPart("body") String body,
                        @RequestPart(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds,
                        @RequestPart(required = false, name = "keywords") Set<String> keywords,
                        @RequestPart(required = false, name = "attachedPictures") List<MultipartFile> attachedPictures) throws IOException {

        // Getting entities
        User currentUser = userService.getByJWT(jwt);

        // Saving entities
        Set<Mention> mentions = mentionService.saveAll(currentUser, userService.getAllById(mentionedUserIds));
        Set<HashTag> hashTags = hashTagService.saveAll(keywords);
        Post post = postService.save(currentUser, body, attachedPictures, mentions, hashTags);
        List<PostMentionNotification> postMentionNotifications = mentionNotificationService.saveAll(currentUser, mentions, post);

        // DTO Conversion
        PostDTO postDTO = postMapper.toDTO(post);
        List<PostMentionNotificationDTO> postMentionNotificationDTOS = postMentionNotifications.stream()
                .map(mentionNotificationMapper::toDTO)
                .toList();

        // Web Socket
        postMentionNotificationDTOS.forEach(notificationWSService::notifyOnMentioned);
        return postDTO;
    }

    @DeleteMapping("/{postId}")
    public void delete(@RequestHeader("Authorization") String jwt,
                       @PathVariable("postId") int postId) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);

        postService.delete(currentUser, post);
        post.getComments().forEach(comment -> commentService.delete(currentUser, post, comment));
    }

    @PatchMapping("/{postId}/comment-section-status")
    public PostDTO updateCommentSectionStatus(@RequestHeader("Authorization") String jwt,
                                              @PathVariable("postId") int postId) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);

        Post updatedPost = postService.updateCommentSectionStatus(currentUser, post);
        return postMapper.toDTO(updatedPost);
    }

    @PutMapping("/{postId}")
    public PostDTO update(@RequestHeader("Authorization") String jwt,
                          @PathVariable("postId") int postId,
                          @RequestPart("newBody") String newBody,
                          @RequestPart(required = false, name = "attachedPictures") List<MultipartFile> attachedPictures) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);

        Post updatedPost = postService.update(currentUser, post, newBody, attachedPictures);
        return postMapper.toDTO(updatedPost);
    }

    @PatchMapping("/{postId}/reactivate")
    public PostDTO reactivate(@RequestHeader("Authorization") String jwt,
                              @PathVariable("postId") int postId) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);

        postService.reactivate(currentUser, post);
        post.getComments().forEach(comment -> commentService.reactivate(currentUser, post, comment));
        return postMapper.toDTO(post);
    }
}
