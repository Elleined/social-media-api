package com.elleined.socialmediaapi.service.post;

import com.elleined.socialmediaapi.exception.BlockedException;
import com.elleined.socialmediaapi.exception.EmptyBodyException;
import com.elleined.socialmediaapi.exception.NotOwnedException;
import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.PostMapper;
import com.elleined.socialmediaapi.model.Comment;
import com.elleined.socialmediaapi.model.Post;
import com.elleined.socialmediaapi.model.Status;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.repository.CommentRepository;
import com.elleined.socialmediaapi.repository.PostRepository;
import com.elleined.socialmediaapi.repository.UserRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.hashtag.entity.PostHashTagService;
import com.elleined.socialmediaapi.service.mention.PostMentionService;
import com.elleined.socialmediaapi.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PostServiceImpl implements PostService {
    private final UserRepository userRepository;
    private final BlockService blockService;

    private final PostMentionService postMentionService;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    private final CommentRepository commentRepository;

    private final PostHashTagService postHashTagService;

    private final Validator validator;


    @Override
    public Post save(User currentUser,
                     String body,
                     MultipartFile attachedPicture,
                     Set<User> mentionedUsers,
                     Set<String> keywords) throws EmptyBodyException, BlockedException, ResourceNotFoundException, IOException {

        if (validator.isNotValid(body))
            throw new EmptyBodyException("Body cannot be empty! Please provide text for your post to be posted!");

        String picture = attachedPicture == null ? null : attachedPicture.getOriginalFilename();
        Post post = postMapper.toEntity(body, currentUser, picture);
        postRepository.save(post);

        if (validator.isValid(mentionedUsers)) postMentionService.mentionAll(currentUser, mentionedUsers, post);
        if (validator.isValid(keywords)) postHashTagService.saveAll(post, keywords);
        log.debug("Post with id of {} saved successfully!", post.getId());
        return post;
    }

    @Override
    public void delete(User currentUser, Post post) throws NotOwnedException {
        if (currentUser.notOwned(post)) throw new NotOwnedException("User with id of " + currentUser.getId() + " doesn't have post with id of " + post.getId());
        post.setStatus(Status.INACTIVE);
        postRepository.save(post);

        List<Comment> comments = post.getComments();
        comments.forEach(comment -> comment.setStatus(Status.INACTIVE));
        commentRepository.saveAll(comments);

        log.debug("Post with id of {} are now inactive", post.getId());
    }

    @Override
    public Post updateBody(User currentUser, Post post, String newBody)
            throws ResourceNotFoundException,
            NotOwnedException {

        if (post.getBody().equals(newBody)) return post;
        if (currentUser.notOwned(post)) throw new NotOwnedException("User with id of " + currentUser.getId() + " doesn't have post with id of " + post.getId());

        post.setBody(newBody);
        postRepository.save(post);
        log.debug("Post with id of {} updated with the new body of {}", post.getId(), newBody);
        return post;
    }

    @Override
    public Post updateCommentSectionStatus(User currentUser, Post post) {
        if (currentUser.notOwned(post)) throw new NotOwnedException("User with id of " + currentUser.getId() + " doesn't have post with id of " + post.getId());

        if (post.isCommentSectionOpen()) post.setCommentSectionStatus(Post.CommentSectionStatus.CLOSED);
        else post.setCommentSectionStatus(Post.CommentSectionStatus.OPEN);

        postRepository.save(post);
        log.debug("Comment section of Post with id of {} are now {}", post.getId(), post.getCommentSectionStatus().name());
        return post;
    }

    @Override
    public Post getById(int postId) throws ResourceNotFoundException {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post with id of " + postId + " does not exists!"));
    }

    @Override
    public List<Post> getAll(User currentUser) {
        return postRepository.findAll().stream()
                .filter(Post::isActive)
                .filter(post -> !blockService.isBlockedBy(currentUser, post.getAuthor()))
                .filter(post -> !blockService.isYouBeenBlockedBy(currentUser, post.getAuthor()))
                .sorted(Comparator.comparing(Post::getDateCreated).reversed())
                .toList();
    }

    @Override
    public Post savedPost(User currentUser, Post postToSaved) {
        if (postToSaved.isInactive()) throw new ResourceNotFoundException("Post with id of " + postToSaved.getId() + " does not exists or already deleted!") ;

        currentUser.getSavedPosts().add(postToSaved);
        postToSaved.getSavingUsers().add(currentUser);

        postRepository.save(postToSaved);
        userRepository.save(currentUser);
        log.debug("User with id of {} saved post successfully with id of {}", currentUser.getId(), postToSaved.getId());
        return postToSaved;
    }

    @Override
    public void unSavedPost(User currentUser, Post postToUnSave) {
        currentUser.getSavedPosts().remove(postToUnSave);
        postToUnSave.getSavingUsers().remove(currentUser);

        postRepository.save(postToUnSave);
        userRepository.save(currentUser);
        log.debug("User with id of {} unsaved post successfully with id of {}", currentUser.getId(), postToUnSave.getId());
    }

    @Override
    public Set<Post> getAllSavedPosts(User currentUser) {
        return currentUser.getSavedPosts().stream()
                .filter(Post::isActive)
                .collect(Collectors.toSet());
    }

    @Override
    public Post sharePost(User currentUser, Post postToShare) {
        if (postToShare.isInactive()) throw new ResourceNotFoundException("Post with id of " + postToShare.getId() + " does not exists or already deleted!") ;

        currentUser.getSharedPosts().add(postToShare);
        postToShare.getSharers().add(currentUser);

        postRepository.save(postToShare);
        userRepository.save(currentUser);
        log.debug("User with id of {} shared post with id of {} successfully", currentUser.getId(), postToShare.getId());
        return postToShare;
    }

    @Override
    public void unSharePost(User currentUser, Post postToUnShare) {
        currentUser.getSharedPosts().remove(postToUnShare);
        postToUnShare.getSharers().remove(currentUser);

        postRepository.save(postToUnShare);
        userRepository.save(currentUser);
        log.debug("User with id of {} shared post with id of {} successfully", currentUser.getId(), postToUnShare.getId());
    }

    @Override
    public Set<Post> getAllSharedPosts(User currentUser) {
        return currentUser.getSharedPosts().stream()
                .filter(Post::isActive)
                .collect(Collectors.toSet());
    }
}
