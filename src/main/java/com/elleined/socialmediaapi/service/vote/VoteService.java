package com.elleined.socialmediaapi.service.vote;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.vote.Vote;
import com.elleined.socialmediaapi.request.vote.VoteRequest;
import com.elleined.socialmediaapi.service.CustomService;

import java.util.List;

public interface VoteService extends CustomService<Vote> {
    Vote save(VoteRequest voteRequest);

    List<Vote> getAll(Post post, Comment comment);

    default List<Vote> getAll(Post post, Comment comment, Vote.Verdict verdict) {
        return this.getAll(post, comment).stream()
                .filter(vote -> vote.getVerdict().equals(verdict))
                .toList();
    }
}
