package com.elleined.socialmediaapi.controller.reply;

import com.elleined.socialmediaapi.dto.main.ReplyDTO;
import com.elleined.socialmediaapi.mapper.main.ReplyMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.main.reply.ReplyService;
import com.elleined.socialmediaapi.service.user.UserService;
import com.elleined.socialmediaapi.service.ws.WSService;
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
@RequestMapping("/users/{currentUserId}/posts/{postId}/comments/{commentId}/replies")
public class ReplyController {

    private final UserService userService;

    private final PostService postService;

    private final CommentService commentService;

    private final ReplyService replyService;
    private final ReplyMapper replyMapper;

    private final WSService wsService;

    @GetMapping
    public List<ReplyDTO> getAllByComment(@PathVariable("currentUserId") int currentUserId,
                                          @PathVariable("postId") int postId,
                                          @PathVariable("commentId") int commentId) {
        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        return replyService.getAllByComment(currentUser, post, comment).stream()
                .map(replyMapper::toDTO)
                .toList();
    }

    @GetMapping("/get-all-by-id")
    public List<ReplyDTO> getAllById(@RequestBody List<Integer> ids) {
        return replyService.getAllById(ids).stream()
                .map(replyMapper::toDTO)
                .toList();
    }

    @PostMapping
    public ReplyDTO save(@PathVariable("currentUserId") int currentUserId,
                         @PathVariable("postId") int postId,
                         @PathVariable("commentId") int commentId,
                         @RequestParam("body") String body,
                         @RequestPart(required = false, name = "attachedPicture") MultipartFile attachedPicture,
                         @RequestParam(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds,
                         @RequestParam(required = false, name = "keywords") Set<String> keywords) throws IOException {

        User currentUser = userService.getById(currentUserId);
        Set<User> mentionedUsers = new HashSet<>(userService.getAllById(mentionedUserIds.stream().toList()));
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        Reply reply = replyService.save(currentUser, post, comment, body, attachedPicture, mentionedUsers, keywords);
        wsService.broadcast(reply);
        return replyMapper.toDTO(reply);
    }

    @DeleteMapping("/{replyId}")
    public ReplyDTO delete(@PathVariable("currentUserId") int currentUserId,
                           @PathVariable("postId") int postId,
                           @PathVariable("commentId") int commentId,
                           @PathVariable("replyId") int replyId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.getById(replyId);

        replyService.delete(currentUser, post, comment, reply);
        wsService.broadcast(reply);
        return replyMapper.toDTO(reply);
    }

    @PatchMapping("/{replyId}")
    public ReplyDTO update(@PathVariable("currentUserId") int currentUserId,
                               @PathVariable("postId") int postId,
                               @PathVariable("commentId") int commentId,
                               @PathVariable("replyId") int replyId,
                               @RequestParam("newBody") String newBody,
                               @RequestParam("newAttachedPicture") String newAttachedPicture) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.getById(replyId);

        Reply updatedReply = replyService.update(currentUser, post, comment, reply, newBody, newAttachedPicture);
        wsService.broadcast(updatedReply);
        return replyMapper.toDTO(updatedReply);
    }
}
