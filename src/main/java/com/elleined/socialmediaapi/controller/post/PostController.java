package com.elleined.socialmediaapi.controller.post;

import com.elleined.socialmediaapi.dto.main.PostDTO;
import com.elleined.socialmediaapi.mapper.main.PostMapper;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/posts")
public class PostController {
    private final UserService userService;

    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping("/{id}")
    public PostDTO getById(@PathVariable("id") int id) {
        Post post = postService.getById(id);
        return postMapper.toDTO(post);
    }

    @GetMapping("/get-all-by-id")
    public List<PostDTO> getAllById(@RequestBody List<Integer> ids) {
        return postService.getAllById(ids).stream()
                .map(postMapper::toDTO)
                .toList();
    }

    @GetMapping
    public List<PostDTO> getAll(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);

        return postService.getAll(currentUser).stream()
                .map(postMapper::toDTO)
                .toList();
    }

    @PostMapping
    public PostDTO save(@PathVariable("currentUserId") int currentUserId,
                        @RequestPart("body") String body,
                        @RequestPart(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds,
                        @RequestPart(required = false, name = "keywords") Set<String> keywords,
                        @RequestPart(required = false, name = "attachedPicture") MultipartFile attachedPicture) throws IOException {

        User currentUser = userService.getById(currentUserId);
        Set<User> mentionedUsers = new HashSet<>(userService.getAllById(mentionedUserIds.stream().toList()));

        Post post = postService.save(currentUser, body, attachedPicture, mentionedUsers, keywords);
        return postMapper.toDTO(post);
    }

    @DeleteMapping("/{postId}")
    public void delete(@PathVariable("currentUserId") int currentUserId,
                       @PathVariable("postId") int postId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        postService.delete(currentUser, post);
    }

    @PatchMapping("/{postId}/comment-section-status")
    public PostDTO updateCommentSectionStatus(@PathVariable("currentUserId") int currentUserId,
                                              @PathVariable("postId") int postId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        Post updatedPost = postService.updateCommentSectionStatus(currentUser, post);
        return postMapper.toDTO(updatedPost);
    }

    @PutMapping("/{postId}")
    public PostDTO update(@PathVariable("currentUserId") int currentUserId,
                          @PathVariable("postId") int postId,
                          @RequestPart("newBody") String newBody,
                          @RequestPart(required = false, name = "newAttachedPicture") MultipartFile newAttachedPicture) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        Post updatedPost = postService.update(currentUser, post, newBody, newAttachedPicture);
        return postMapper.toDTO(updatedPost);
    }

    @PatchMapping("/{postId}/reactivate")
    public PostDTO reactivate(@PathVariable("currentUserId") int currentUserId,
                              @PathVariable("postId") int postId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);

        postService.reactivate(currentUser, post);
        return postMapper.toDTO(post);
    }
}
