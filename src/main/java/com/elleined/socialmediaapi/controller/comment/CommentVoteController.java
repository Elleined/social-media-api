package com.elleined.socialmediaapi.controller.comment;

import com.elleined.socialmediaapi.dto.main.VoteDTO;
import com.elleined.socialmediaapi.mapper.vote.VoteMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.vote.Vote;
import com.elleined.socialmediaapi.request.vote.VoteRequest;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.vote.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/posts/{postId}/comments/{commentId}/votes")
public class CommentVoteController {
    private final VoteService voteService;
    private final VoteMapper voteMapper;

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping
    public List<VoteDTO> getAll(@PathVariable("currentUserId") int currentUserId,
                                @PathVariable("postId") int postId,
                                @PathVariable("commentId") int commentId) {

        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        return voteService.getAll(post, comment).stream()
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

        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        return voteService.getAll(post, comment, verdict).stream()
                .map(voteMapper::toDTO)
                .toList();
    }

    @PostMapping
    public VoteDTO save(@Valid @RequestBody VoteRequest voteRequest) {
        Vote vote = voteService.save(voteRequest);
        return voteMapper.toDTO(vote);
    }
}
