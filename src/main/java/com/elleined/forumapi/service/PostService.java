package com.elleined.forumapi.service;

import com.elleined.forumapi.exception.BlockedException;
import com.elleined.forumapi.exception.EmptyBodyException;
import com.elleined.forumapi.exception.NotOwnedException;
import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.Post.CommentSectionStatus;
import com.elleined.forumapi.model.Status;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.repository.CommentRepository;
import com.elleined.forumapi.repository.PostRepository;
import com.elleined.forumapi.service.image.ImageUploader;
import com.elleined.forumapi.service.mention.MentionService;
import com.elleined.forumapi.utils.DirectoryFolders;
import com.elleined.forumapi.validator.StringValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PostService {
    private final BlockService blockService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final ImageUploader imageUploader;

    private final MentionService<PostMention, Post> postMentionService;

    @Value("${cropTrade.img.directory}")
    private String cropTradeImgDirectory;

    public Post save(User currentUser, String body, MultipartFile attachedPicture, Set<User> mentionedUsers)
            throws EmptyBodyException,
            BlockedException,
            ResourceNotFoundException,
            IOException {

        if (StringValidator.isNotValidBody(body))
            throw new EmptyBodyException("Body cannot be empty! Please provide text for your post to be posted!");

        Post post = Post.builder()
                .body(body)
                .dateCreated(LocalDateTime.now())
                .author(currentUser)
                .status(Status.ACTIVE)
                .commentSectionStatus(CommentSectionStatus.OPEN)
                .mentions(new HashSet<>())
                .likes(new HashSet<>())
                .comments(new ArrayList<>())
                .attachedPicture(attachedPicture == null ? null : attachedPicture.getOriginalFilename())
                .build();
        currentUser.getPosts().add(post);
        postRepository.save(post);

        if (attachedPicture != null) imageUploader.upload(cropTradeImgDirectory + DirectoryFolders.POST_PICTURES_FOLDER, attachedPicture);
        if (mentionedUsers != null) postMentionService.mentionAll(currentUser, mentionedUsers, post);
        log.debug("Post with id of {} saved successfully!", post.getId());
        return post;
    }

    public void delete(User currentUser, Post post) throws NotOwnedException {
        if (currentUser.notOwned(post)) throw new NotOwnedException("User with id of " + currentUser.getId() + " doesn't have post with id of " + post.getId());
        post.setStatus(Status.INACTIVE);
        postRepository.save(post);

        List<Comment> comments = post.getComments();
        comments.forEach(comment -> comment.setStatus(Status.INACTIVE));
        commentRepository.saveAll(comments);

        log.debug("Post with id of {} are now inactive", post.getId());
    }

    public Post updateBody(Post post, String newBody) {
        post.setBody(newBody);
        postRepository.save(post);
        log.debug("Post with id of {} updated with the new body of {}", post.getId(), newBody);
        return post;
    }

    public Post updateCommentSectionStatus(Post post) {
        if (post.getCommentSectionStatus() == CommentSectionStatus.OPEN) {
            post.setCommentSectionStatus(CommentSectionStatus.CLOSED);
        } else {
            post.setCommentSectionStatus(CommentSectionStatus.OPEN);
        }
        postRepository.save(post);
        log.debug("Comment section of Post with id of {} are now {}", post.getId(), post.getCommentSectionStatus().name());
        return post;
    }

    public Post getById(int postId) throws ResourceNotFoundException {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post with id of " + postId + " does not exists!"));
    }

    public List<Post> getAll(User currentUser) {
        return postRepository.findAll()
                .stream()
                .filter(post -> post.getStatus() == Status.ACTIVE)
                .filter(post -> !blockService.isBlockedBy(currentUser, post.getAuthor()))
                .filter(post -> !blockService.isYouBeenBlockedBy(currentUser, post.getAuthor()))
                .sorted(Comparator.comparing(Post::getDateCreated).reversed())
                .toList();
    }

    public Optional<Comment> getPinnedComment(Post post) throws ResourceNotFoundException {
        Comment pinnedComment = post.getPinnedComment();
        if (post.isDeleted())
            throw new ResourceNotFoundException("Post with id of " + post.getId() + " might already been deleted or does not exists anymore!");

        if (pinnedComment == null) return Optional.empty();
        return Optional.of( pinnedComment );
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
