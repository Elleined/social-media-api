package com.elleined.socialmediaapi.service.pin;

import com.elleined.socialmediaapi.exception.NotOwnedException;
import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostPinCommentService implements PinService<Post, Comment> {
    private final PostRepository postRepository;

    @Override
    public void pin(User currentUser, Post post, Comment comment) throws NotOwnedException, ResourceNotFoundException {
        if (currentUser.notOwned(post)) throw new NotOwnedException("User with id of " + currentUser.getId() + " does not own post with id of " + post.getId() + " for him/her to pin a comment in this post!");
        if (!post.has(comment)) throw new NotOwnedException("Post with id of " + post.getId() + " doesn't have comment with id of " + comment.getId());
        if (comment.isInactive()) throw new ResourceNotFoundException("Comment with id of " + comment.getId() + " you specify is already deleted or doesn't exist anymore!");

        post.setPinnedComment(comment);
        postRepository.save(post);
        log.debug("Author with id of {} pinned comment with id {} in his/her post with id of {}", post.getCreator().getId(), comment.getId(), post.getId());
    }

    @Override
    public void unpin(Post post) {
        post.setPinnedComment(null);
        postRepository.save(post);
        log.debug("Post pinned comment unpinned successfully");
    }

    @Override
    public Comment getPinned(Post post) throws ResourceNotFoundException {
        if (post.isInactive())
            throw new ResourceNotFoundException("Post with id of " + post.getId() + " might already been deleted or does not exists anymore!");

        return post.getPinnedComment();
    }
}
