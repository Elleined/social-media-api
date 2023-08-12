package com.forum.application.service;

import com.forum.application.exception.ResourceNotFoundException;
import com.forum.application.model.Comment;
import com.forum.application.model.Post;
import com.forum.application.model.Post.CommentSectionStatus;
import com.forum.application.model.Status;
import com.forum.application.model.User;
import com.forum.application.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PostService {
    private final BlockService blockService;
    private final PostRepository postRepository;
    private final CommentService commentService;

    Post save(User currentUser, String body, String attachedPicture) {
        Post post = Post.builder()
                .body(body)
                .dateCreated(LocalDateTime.now())
                .author(currentUser)
                .status(Status.ACTIVE)
                .commentSectionStatus(CommentSectionStatus.OPEN)
                .mentions(new HashSet<>())
                .likes(new HashSet<>())
                .comments(new ArrayList<>())
                .attachedPicture(attachedPicture)
                .build();

        currentUser.getPosts().add(post);
        postRepository.save(post);
        log.debug("Post with id of {} saved successfully!", post.getId());
        return post;
    }

    void delete(Post post) {
        post.setStatus(Status.INACTIVE);
        postRepository.save(post);
        log.debug("Post with id of {} are now inactive", post.getId());
        post.getComments().forEach(commentService::delete);
    }

    void pinComment(Post post, Comment comment) {
        post.setPinnedComment(comment);
        postRepository.save(post);
        log.debug("Author with id of {} pinned comment with id {} in his/her post with id of {}", post.getAuthor().getId(), comment.getId(), post.getId());
    }

    void updateBody(Post post, String newBody) {
        post.setBody(newBody);
        postRepository.save(post);
        log.debug("Post with id of {} updated with the new body of {}", post.getId(), newBody);
    }

    void updateCommentSectionStatus(Post post) {
        if (post.getCommentSectionStatus() == CommentSectionStatus.OPEN) {
            post.setCommentSectionStatus(CommentSectionStatus.CLOSED);
        } else {
            post.setCommentSectionStatus(CommentSectionStatus.OPEN);
        }
        postRepository.save(post);
        log.debug("Comment section of Post with id of {} are now {}", post.getId(), post.getCommentSectionStatus().name());
    }

    public Post getById(int postId) throws ResourceNotFoundException {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post with id of " + postId + " does not exists!"));
    }

    List<Post> getAll(User currentUser) {
        return postRepository.findAll()
                .stream()
                .filter(post -> post.getStatus() == Status.ACTIVE)
                .filter(post -> !blockService.isBlockedBy(currentUser, post.getAuthor()))
                .filter(post -> !blockService.isYouBeenBlockedBy(currentUser, post.getAuthor()))
                .sorted(Comparator.comparing(Post::getDateCreated).reversed())
                .toList();
    }

    List<Post> getAllByAuthorId(User author) {
        return author.getPosts().stream()
                .filter(post -> post.getStatus() == Status.ACTIVE)
                .toList();
    }

    boolean isCommentSectionClosed(Post post) {
        return post.getCommentSectionStatus() == CommentSectionStatus.CLOSED;
    }

    public boolean isDeleted(Post post) {
        return post.getStatus() == Status.INACTIVE;
    }

    boolean isUserNotOwnedPost(User currentUser, Post post) {
        return currentUser.getPosts().stream().noneMatch(post::equals);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public int getTotalCommentsAndReplies(Post post) {
        int commentCount = (int) post.getComments()
                .stream()
                .filter(comment -> comment.getStatus() == Status.ACTIVE)
                .count();

        int commentRepliesCount = (int) post.getComments()
                .stream()
                .map(Comment::getReplies)
                .flatMap(replies -> replies.stream().filter(reply -> reply.getStatus() == Status.ACTIVE))
                .count();

        return commentCount + commentRepliesCount;
    }
}
