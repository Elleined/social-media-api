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

    private final UserService userService;
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

    void delete(int postId) throws ResourceNotFoundException {
        Post post = getById(postId);
        this.setStatus(post);
        log.debug("Post with id of {} are now inactive", postId);
    }

    Post updatePostBody(int postId, String newBody) throws ResourceNotFoundException {
        Post post = getById(postId);
        if (post.getBody().equals(newBody)) return post; // Returning if user doesn't change the post body
        post.setBody(newBody);
        log.debug("Post with id of {} updated with the new body of {}", postId, newBody);
        return postRepository.save(post);
    }

    Post updateCommentSectionStatus(int postId) throws ResourceNotFoundException {
        Post post = getById(postId);
        if (post.getCommentSectionStatus() == CommentSectionStatus.OPEN) {
            post.setCommentSectionStatus(CommentSectionStatus.CLOSED);
        } else {
            post.setCommentSectionStatus(CommentSectionStatus.OPEN);
        }
        log.debug("Comment section of Post with id of {} are now {}", postId, post.getCommentSectionStatus().name());
        return postRepository.save(post);
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

    List<Post> getAllByAuthorId(int authorId) throws ResourceNotFoundException {
        if (!userService.existsById(authorId)) throw new ResourceNotFoundException("User with id of " + authorId + " does not exists");
        return postRepository.fetchAllByAuthorId(authorId)
                .stream()
                .filter(post -> post.getStatus() == Status.ACTIVE)
                .sorted(Comparator.comparing(Post::getDateCreated).reversed())
                .toList();
    }

    boolean isCommentSectionClosed(int postId) throws ResourceNotFoundException {
        Post post = getById(postId);
        return post.getCommentSectionStatus() == CommentSectionStatus.CLOSED;
    }

    boolean isDeleted(int postId) throws ResourceNotFoundException {
        Post post = getById(postId);
        return post.getStatus() == Status.INACTIVE;
    }

    public boolean isDeleted(Post post) throws ResourceNotFoundException {
        return post.getStatus() == Status.INACTIVE;
    }

    public String getCommentSectionStatus(int postId) throws ResourceNotFoundException {
        Post post = getById(postId);
        return post.getCommentSectionStatus().name();
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

    private void setStatus(Post post) throws ResourceNotFoundException {
        post.setStatus(Status.INACTIVE);
        post.getComments().forEach(commentService::setStatus);
    }

}
