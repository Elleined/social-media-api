package com.elleined.socialmediaapi.controller.comment;

import com.elleined.socialmediaapi.dto.vote.VoteDTO;
import com.elleined.socialmediaapi.mapper.vote.VoteMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.vote.Vote;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.user.UserService;
import com.elleined.socialmediaapi.service.vote.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/posts/{postId}/comments/{commentId}/votes")
public class CommentVoteController {
    private final UserService userService;

    private final VoteService voteService;
    private final VoteMapper voteMapper;

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping
    public List<VoteDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                @PathVariable("postId") int postId,
                                @PathVariable("commentId") int commentId) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        return voteService.getAll(currentUser, post, comment).stream()
                .map(voteMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public VoteDTO getById(@PathVariable("id") int id) {
        Vote vote = voteService.getById(id);
        return voteMapper.toDTO(vote);
    }

    @GetMapping("/get-all-by-id")
    public List<VoteDTO> getAllById(@RequestBody List<Integer> ids) {
        return voteService.getAllById(ids).stream()
                .map(voteMapper::toDTO)
                .toList();
    }

    @GetMapping("/verdict")
    public List<VoteDTO> getAllByVerdict(@PathVariable("currentUserId") int currentUserId,
                                         @PathVariable("postId") int postId,
                                         @PathVariable("commentId") int commentId,
                                         @RequestParam("verdict") Vote.Verdict verdict) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        return voteService.getAll(currentUser, post, comment, verdict).stream()
                .map(voteMapper::toDTO)
                .toList();
    }

    @PostMapping
    public VoteDTO save(@PathVariable("currentUserId") int currentUserId,
                        @PathVariable("postId") int postId,
                        @PathVariable("commentId") int commentId,
                        @RequestParam("verdict") Vote.Verdict verdict) {

        User currentUser = userService.getById(currentUserId);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        Vote vote = voteService.save(currentUser, post, comment, verdict);
        return voteMapper.toDTO(vote);
    }
}
