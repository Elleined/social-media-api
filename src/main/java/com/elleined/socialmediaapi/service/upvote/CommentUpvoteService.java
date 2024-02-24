package com.elleined.socialmediaapi.service.upvote;

import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.UpvoteException;
import com.elleined.socialmediaapi.model.Comment;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.repository.CommentRepository;
import com.elleined.socialmediaapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentUpvoteService implements UpvoteService<Comment> {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public Comment upvote(User respondent, Comment comment) {

        if (comment.isInactive()) throw new ResourceNotFoundException("The comment you trying to upvote might be deleted by the author or does not exists anymore!");
        if (respondent.isAlreadyUpvoted(comment)) throw new UpvoteException("You can only up vote and down vote a comment once!");

        comment.getUpvotingUsers().add(respondent);
        respondent.getUpvotedComments().add(comment);

        commentRepository.save(comment);
        userRepository.save(respondent);
        log.debug("User with id of {} upvoted the Comment with id of {} successfully", respondent.getId(), comment.getId());
        return comment;
    }
}
