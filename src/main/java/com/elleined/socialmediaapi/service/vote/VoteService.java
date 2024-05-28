package com.elleined.socialmediaapi.service.vote;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.vote.Vote;
import com.elleined.socialmediaapi.service.CustomService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoteService extends CustomService<Vote> {
    Vote save(User currentUser, Post post, Comment comment, Vote.Verdict verdict);

    List<Vote> getAll(User currentUser, Post post, Comment comment, Pageable pageable);

    default List<Vote> getAll(User currentUser, Post post, Comment comment, Vote.Verdict verdict, Pageable pageable) {
        return this.getAll(currentUser, post, comment, pageable).stream()
                .filter(vote -> vote.getVerdict().equals(verdict))
                .toList();
    }
}
