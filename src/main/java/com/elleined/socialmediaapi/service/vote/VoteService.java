package com.elleined.socialmediaapi.service.vote;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.vote.Vote;
import com.elleined.socialmediaapi.service.CustomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoteService extends CustomService<Vote> {
    Vote save(User currentUser, Post post, Comment comment, Vote.Verdict verdict);
    Page<Vote> getAll(User currentUser, Post post, Comment comment, Pageable pageable);
    Page<Vote> getAll(User currentUser, Post post, Comment comment, Vote.Verdict verdict, Pageable pageable);
}
