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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/posts/{postId}/comments/{commentId}/votes")
public class CommentVoteController {
    private final UserService userService;

    private final VoteService voteService;
    private final VoteMapper voteMapper;

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping
    public Page<VoteDTO> getAll(@RequestHeader("Authorization") String jwt,
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

        return voteService.getAll(currentUser, post, comment, pageable)
                .map(voteMapper::toDTO);
    }

    @GetMapping("/{id}")
    public VoteDTO getById(@PathVariable("id") int id) {
        Vote vote = voteService.getById(id);
        return voteMapper.toDTO(vote);
    }

    @GetMapping("/verdict")
    public Page<VoteDTO> getAllByVerdict(@RequestHeader("Authorization") String jwt,
                                         @PathVariable("postId") int postId,
                                         @PathVariable("commentId") int commentId,
                                         @RequestParam("verdict") Vote.Verdict verdict,
                                         @RequestParam(required = false, defaultValue = "1", value = "pageNumber") int pageNumber,
                                         @RequestParam(required = false, defaultValue = "5", value = "pageSize") int pageSize,
                                         @RequestParam(required = false, defaultValue = "ASC", value = "sortDirection") Sort.Direction direction,
                                         @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, direction, sortBy);

        return voteService.getAll(currentUser, post, comment, verdict, pageable)
                .map(voteMapper::toDTO);
    }

    @PostMapping
    public VoteDTO save(@RequestHeader("Authorization") String jwt,
                        @PathVariable("postId") int postId,
                        @PathVariable("commentId") int commentId,
                        @RequestParam("verdict") Vote.Verdict verdict) {

        User currentUser = userService.getByJWT(jwt);
        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        Vote vote = voteService.save(currentUser, post, comment, verdict);
        return voteMapper.toDTO(vote);
    }
}
