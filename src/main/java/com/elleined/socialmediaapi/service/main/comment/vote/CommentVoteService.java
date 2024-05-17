package com.elleined.socialmediaapi.service.main.comment.vote;

import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.UpvoteException;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.CommentRepository;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentVoteService implements VoteService<Comment> {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public Comment upVote(User respondent, Comment comment) {

        if (comment.isInactive()) throw new ResourceNotFoundException("The comment you trying to upvote might be deleted by the author or does not exists anymore!");
        if (respondent.isAlreadyUpvoted(comment)) throw new UpvoteException("You can only up vote and down vote a comment once!");

        comment.getUserVotes().add(respondent);
        respondent.getVotedComments().add(comment);

        commentRepository.save(comment);
        userRepository.save(respondent);
        log.debug("User with id of {} upvoted the Comment with id of {} successfully", respondent.getId(), comment.getId());
        return comment;
    }

    @Override
    public Comment downVote(User respondent, Comment comment) {
        return null;
    }
}
