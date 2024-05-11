package com.elleined.socialmediaapi.service.pin;

import com.elleined.socialmediaapi.exception.NotOwnedException;
import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.main.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentPinReplyService implements PinService<Comment, Reply> {
    private final CommentRepository commentRepository;

    @Override
    public void pin(User currentUser, Comment comment, Reply reply) throws NotOwnedException, ResourceNotFoundException {
        if (currentUser.notOwned(comment)) throw new NotOwnedException("User with id of " + currentUser.getId() + " does not owned comment with id of " + comment.getId() + " for him/her to pin a reply in this comment!");
        if (comment.doesNotHave(reply)) throw new NotOwnedException("Comment with id of " + comment.getId() + " doesnt have reply of " + reply.getId());
        if (reply.isInactive()) throw new ResourceNotFoundException("Reply with id of " + reply.getId() + " you specify is already deleted or does not exists anymore!");

        comment.setPinnedReply(reply);
        commentRepository.save(comment);
        log.debug("Comment author with id of {} pinned reply with id of {} in his/her comment with id of {}", comment.getCommenter().getId(), reply.getId(), comment.getId());
    }

    @Override
    public void unpin(Comment comment) {
        comment.setPinnedReply(null);
        commentRepository.save(comment);
        log.debug("Comment pinned reply unpinned successfully");
    }

    @Override
    public Reply getPinned(Comment comment) throws ResourceNotFoundException {
        if (comment.isInactive())
            throw new ResourceNotFoundException("Comment with id of " + comment.getId() + " might already been deleted or does not exists!");

        return comment.getPinnedReply();
    }
}
