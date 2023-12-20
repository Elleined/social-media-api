package com.elleined.forumapi.controller.post;

import com.elleined.forumapi.dto.CommentDTO;
import com.elleined.forumapi.dto.PostDTO;
import com.elleined.forumapi.mapper.CommentMapper;
import com.elleined.forumapi.mapper.PostMapper;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.PostLike;
import com.elleined.forumapi.service.CommentService;
import com.elleined.forumapi.service.ModalTrackerService;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.notification.reader.post.PostLikeNotificationReader;
import com.elleined.forumapi.service.notification.reader.post.PostMentionNotificationReader;
import com.elleined.forumapi.service.post.PostService;
import com.elleined.forumapi.service.ws.WSNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/posts")
public class PostController {
    private final UserService userService;

    private final WSNotificationService wsNotificationService;

    private final PostService postService;
    private final PostMapper postMapper;

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    private final ModalTrackerService modalTrackerService;

    private final PostLikeNotificationReader postLikeNotificationReader;
    private final PostMentionNotificationReader postMentionNotificationReader;

    @GetMapping
    public List<PostDTO> getAll(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);

        postLikeNotificationReader.readAll(currentUser);
        postMentionNotificationReader.readAll(currentUser);

        modalTrackerService.saveTrackerOfUserById(currentUserId, 0, "POST");
        return postService.getAll(currentUser).stream()
                .map(postMapper::toDTO)
                .toList();
    }

    @GetMapping("/getPinnedComment/{postId}")
    public Optional<CommentDTO> getPinnedComment(@PathVariable("postId") int postId) {
        Post post = postService.getById(postId);
        Optional<Comment> pinnedComment = postService.getPinnedComment(post);

        return Optional.of( commentMapper.toDTO(pinnedComment.orElseThrow()) );
    }

    @PostMapping
    public PostDTO save(@PathVariable("currentUserId") int currentUserId,
                        @RequestParam("body") String body,
                        @RequestPart(required = false, name = "attachedPicture") MultipartFile attachedPicture,
                        @RequestParam(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds) throws IOException {

        User currentUser = userService.getById(currentUserId);
        Set<User> mentionedUsers = userService.getAllById(mentionedUserIds);
        Post post = postService.save(currentUser, body, attachedPicture, mentionedUsers);

        if (mentionedUsers != null) wsNotificationService.broadcastPostMentions(post.getMentions());
        return postMapper.toDTO(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostDTO> delete(@PathVariable("currentUserId") int currentUserId,
                                              @PathVariable("postId") int postId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        postService.delete(currentUser, post);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/commentSectionStatus/{postId}")
    public PostDTO updateCommentSectionStatus(@PathVariable("currentUserId") int currentUserId,
                                              @PathVariable("postId") int postId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        Post updatedPost = postService.updateCommentSectionStatus(currentUser, post);
        return postMapper.toDTO(updatedPost);
    }

    @PatchMapping("/body/{postId}")
    public PostDTO updateBody(@PathVariable("currentUserId") int currentUserId,
                                  @PathVariable("postId") int postId,
                                  @RequestParam("newPostBody") String newPostBody) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        Post updatedPost = postService.updateBody(currentUser, post, newPostBody);
        return postMapper.toDTO(updatedPost);
    }

    @PatchMapping("/like/{postId}")
    public PostDTO like(@PathVariable("currentUserId") int respondentId,
                            @PathVariable("postId") int postId) {

        User respondent = userService.getById(respondentId);
        Post post = postService.getById(postId);

        if (postService.isLiked(respondent, post)) {
            postService.unLike(respondent, post);
            return postMapper.toDTO(post);
        }

        PostLike postLike = postService.like(respondent, post);
        wsNotificationService.broadcastLike(postLike);
        return postMapper.toDTO(post);
    }

    @PatchMapping("/{postId}/pinComment/{commentId}")
    public PostDTO pinComment(@PathVariable("currentUserId") int currentUserId,
                              @PathVariable("postId") int postId,
                              @PathVariable("commentId") int commentId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        postService.pin(currentUser, post, comment);
        return postMapper.toDTO(post);
    }
}
