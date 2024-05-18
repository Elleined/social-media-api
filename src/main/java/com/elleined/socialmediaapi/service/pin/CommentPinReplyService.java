package com.elleined.socialmediaapi.service.pin;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.CommentRepository;
import com.elleined.socialmediaapi.service.main.comment.CommentServiceRestriction;
import com.elleined.socialmediaapi.service.user.UserServiceRestriction;
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

    private final UserServiceRestriction userServiceRestriction;
    private final CommentServiceRestriction commentServiceRestriction;


    @Override
    public void pin(User currentUser, Comment comment, Reply reply) throws ResourceNotOwnedException, ResourceNotFoundException {
        if (userServiceRestriction.notOwned(currentUser, comment))
            throw new ResourceNotOwnedException("Cannot pin reply! because user with id of " + currentUser.getId() + " does not owned comment with id of " + comment.getId() + " for him/her to pin a reply in this comment!");

        if (commentServiceRestriction.notOwned(comment, reply))
            throw new ResourceNotOwnedException("Cannot pin reply! because comment with id of " + comment.getId() + " doesnt have reply of " + reply.getId());

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot pin reply! because comment might already been deleted or doesn't exists!");

        if (reply.isInactive())
            throw new ResourceNotFoundException("Cannot pin reply! because reply with id of " + reply.getId() + " you specify is already deleted or does not exists anymore!");

        comment.setPinnedReply(reply);
        commentRepository.save(comment);
        log.debug("Comment author with id of {} pinned reply with id of {} in his/her comment with id of {}", comment.getCreator().getId(), reply.getId(), comment.getId());
    }

    @Override
    public void unpin(Comment comment) {
        if (commentServiceRestriction.doesNotHavePinnedReply(comment))
            throw new ResourceNotFoundException("Cannot unpin! because there's no pinned reply!");

        comment.setPinnedReply(null);
        commentRepository.save(comment);
        log.debug("Comment pinned reply unpinned successfully");
    }
}
