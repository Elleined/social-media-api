package com.elleined.socialmediaapi.service.pin;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.PostRepository;
import com.elleined.socialmediaapi.service.main.post.PostServiceRestriction;
import com.elleined.socialmediaapi.service.user.UserServiceRestriction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class PostPinCommentService implements PinService<Post, Comment> {
    private final PostRepository postRepository;

    private final UserServiceRestriction userServiceRestriction;
    private final PostServiceRestriction postServiceRestriction;

    @Override
    public void pin(User currentUser, Post post, Comment comment) {
        if (userServiceRestriction.notOwned(currentUser, post))
            throw new ResourceNotOwnedException("Cannot pin comment! because user with id of " + currentUser.getId() + " does not own post with id of " + post.getId() + " for him/her to pin a comment in this post!");

        if (postServiceRestriction.notOwned(post, comment))
            throw new ResourceNotOwnedException("Cannot pin comment! because post with id of " + post.getId() + " doesn't have comment with id of " + comment.getId());

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot pin comment! because this post might already been deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot pin comment! because comment with id of " + comment.getId() + " you specify is already deleted or doesn't exist anymore!");

        post.setPinnedComment(comment);
        postRepository.save(post);
        log.debug("Author with id of {} pinned comment with id {} in his/her post with id of {}", post.getCreator().getId(), comment.getId(), post.getId());
    }

    @Override
    public void unpin(Post post) {
        if (postServiceRestriction.doesNotHavePinnedComment(post))
            throw new ResourceNotFoundException("Cannot unpin comment! because there's no pinned comment!");

        post.setPinnedComment(null);
        postRepository.save(post);
        log.debug("Post pinned comment unpinned successfully");
    }
}
