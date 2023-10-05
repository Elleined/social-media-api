package com.elleined.forumapi.controller;

import com.elleined.forumapi.dto.CommentDTO;
import com.elleined.forumapi.dto.PostDTO;
import com.elleined.forumapi.service.ForumService;
import jakarta.servlet.http.HttpSession;
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

    private final ForumService forumService;
    @GetMapping
    public List<PostDTO> getAllPost(@PathVariable("currentUserId") int currentUserId,
                                    HttpSession session) {
        session.setAttribute("currentUserId", currentUserId);
        return forumService.getAllPost(currentUserId);
    }

    @GetMapping("/author")
    public List<PostDTO> getAllByAuthorId(@PathVariable("currentUserId") int authorId) {
        return forumService.getAllByAuthorId(authorId);
    }

    @GetMapping("/getPinnedComment/{postId}")
    public Optional<CommentDTO> getPinnedComment(@PathVariable("postId") int postId) {
        return forumService.getPinnedComment(postId);
    }

    @PostMapping
    public PostDTO savePost(@PathVariable("currentUserId") int currentUserId,
                            @RequestParam("body") String body,
                            @RequestPart(required = false, name = "attachedPicture") MultipartFile attachedPicture,
                            @RequestParam(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds) throws IOException {

        return forumService.savePost(currentUserId, body, attachedPicture, mentionedUserIds);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostDTO> deletePost(@PathVariable("currentUserId") int currentUserId,
                                              @PathVariable("postId") int postId) {

        forumService.deletePost(currentUserId, postId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/commentSectionStatus/{postId}")
    public PostDTO updateCommentSectionStatus(@PathVariable("currentUserId") int currentUserId,
                                              @PathVariable("postId") int postId) {

        return forumService.updateCommentSectionStatus(currentUserId, postId);
    }

    @PatchMapping("/body/{postId}")
    public PostDTO updatePostBody(@PathVariable("currentUserId") int currentUserId,
                                  @PathVariable("postId") int postId,
                                  @RequestParam("newPostBody") String newPostBody) {

        return forumService.updatePostBody(currentUserId, postId, newPostBody);
    }

    @PatchMapping("/like/{postId}")
    public PostDTO likePost(@PathVariable("currentUserId") int respondentId,
                            @PathVariable("postId") int postId) {

        return forumService.likePost(respondentId, postId);
    }

    @PatchMapping("/{postId}/pinComment/{commentId}")
    public PostDTO pinComment(@PathVariable("currentUserId") int currentUserId,
                              @PathVariable("postId") int postId,
                              @PathVariable("commentId") int commentId) {

        return forumService.pinComment(currentUserId, postId, commentId);
    }
}
