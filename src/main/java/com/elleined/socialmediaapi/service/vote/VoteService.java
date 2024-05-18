package com.elleined.socialmediaapi.service.vote;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.vote.Vote;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.request.main.VoteRequest;
import com.elleined.socialmediaapi.service.CustomService;

import java.util.List;

public interface VoteService extends CustomService<Vote> {
    Vote save(VoteRequest voteRequest);

    List<Vote> getAll(Comment comment);

    default List<Vote> getAllDownVote(Comment comment) {
        return this.getAll(comment).stream()
                .filter(vote -> vote.getVerdict().equals(Vote.Verdict.DOWN_VOTE))
                .toList();
    }

    default List<Vote> getAllUpVote(Comment comment) {
        return this.getAll(comment).stream()
                .filter(vote -> vote.getVerdict().equals(Vote.Verdict.UP_VOTE))
                .toList();
    }
}
