package com.elleined.socialmediaapi.service.main.post;

import com.elleined.socialmediaapi.exception.field.FieldException;
import com.elleined.socialmediaapi.exception.resource.ResourceAlreadyExistsException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.mapper.main.PostMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.PostRepository;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.user.UserServiceRestriction;
import com.elleined.socialmediaapi.validator.FieldValidator;
import com.elleined.socialmediaapi.validator.PageableUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService, PostServiceRestriction {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    private final UserRepository userRepository;

    private final BlockService blockService;

    private final UserServiceRestriction userServiceRestriction;

    private final FieldValidator fieldValidator;

    @Override
    public Post save(User currentUser,
                     String body,
                     List<MultipartFile> attachedPictures,
                     Set<Mention> mentions,
                     Set<HashTag> hashTags) {

        if (fieldValidator.isNotValid(body))
            throw new FieldException("Cannot save post! because body cannot be empty! Please provide text for your post to be posted!");

        List<String> pictures = attachedPictures.stream()
                .map(MultipartFile::getOriginalFilename)
                .toList();

        Post post = postMapper.toEntity(currentUser, body, pictures, hashTags, mentions);
        postRepository.save(post);

        log.debug("Post with id of {} saved successfully!", post.getId());
        return post;
    }

    @Override
    public void delete(User currentUser, Post post) throws ResourceNotOwnedException {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot delete post! because post might already been deleted or doesn't exists!");

        if (userServiceRestriction.notOwned(currentUser, post))
            throw new ResourceNotOwnedException("Cannot delete post! because user with id of " + currentUser.getId() + " doesn't have post with id of " + post.getId());

        updateStatus(currentUser, post, Forum.Status.INACTIVE);
        log.debug("Post with id of {} and related comments are now inactive", post.getId());
    }

    @Override
    public Post update(User currentUser, Post post, String newBody, List<MultipartFile> attachedPictures)
            throws ResourceNotFoundException,
            ResourceNotOwnedException {

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot delete post! because post might already been deleted or doesn't exists!");

        if (post.getBody().equals(newBody))
            return post;

        if (userServiceRestriction.notOwned(currentUser, post))
            throw new ResourceNotOwnedException("Cannot update post! because user with id of " + currentUser.getId() + " doesn't have post with id of " + post.getId());

        List<String> pictures = attachedPictures.stream()
                .map(MultipartFile::getOriginalFilename)
                .toList();

        post.setBody(newBody);
        post.setAttachedPictures(pictures);
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);
        log.debug("Post with id of {} updated with the new body of {}", post.getId(), newBody);
        return post;
    }

    @Override
    public Post updateCommentSectionStatus(User currentUser, Post post) {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot delete post! because post might already been deleted or doesn't exists!");

        if (userServiceRestriction.notOwned(currentUser, post))
            throw new ResourceNotOwnedException("Cannot update comment section! because user with id of " + currentUser.getId() + " doesn't have post with id of " + post.getId());

        if (isCommentSectionOpen(post))
            post.setCommentSectionStatus(Post.CommentSectionStatus.CLOSED);
        else
            post.setCommentSectionStatus(Post.CommentSectionStatus.OPEN);

        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
        log.debug("Comment section of Post with id of {} are now {}", post.getId(), post.getCommentSectionStatus().name());
        return post;
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getById(int postId) throws ResourceNotFoundException {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post with id of " + postId + " does not exists!"));
    }

    @Override
    public Page<Post> getAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> getAll(User currentUser, Pageable pageable) {
        List<Post> posts = postRepository.findAll(pageable).stream()
                .filter(Post::isActive)
                .filter(post -> !blockService.isBlockedByYou(currentUser, post.getCreator()))
                .filter(post -> !blockService.isYouBeenBlockedBy(currentUser, post.getCreator()))
                .toList();
        return PageableUtil.toPage(posts, pageable);
    }

    @Override
    public void reactivate(User currentUser, Post post) {
        if (post.isActive())
            throw new ResourceNotFoundException("Cannot reactivate post! because post are not deleted or doesn't exists!");

        if (userServiceRestriction.notOwned(currentUser, post))
            throw new ResourceNotOwnedException("Cannot reactivate post! because user with id of " + currentUser.getId() + " doesn't have post with id of " + post.getId());

        updateStatus(currentUser, post, Forum.Status.ACTIVE);
        log.debug("Post with of {} and related comment are now active!", post.getId());
    }

    @Override
    public void updateStatus(User currentUser, Post post, Forum.Status status) {
        if (userServiceRestriction.notOwned(currentUser, post))
            throw new ResourceNotOwnedException("Cannot update post status! because user with id of " + currentUser.getId() + " doesn't have post with id of " + post.getId());

        post.setStatus(status);
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
        log.debug("Updating post status with id of {} success to {}", post.getId(), status );
    }

    @Override
    public Post savedPost(User currentUser, Post postToSaved) {
        if (postToSaved.isInactive())
            throw new ResourceNotFoundException("Cannot saved post! because post with id of " + postToSaved.getId() + " does not exists or already deleted!") ;

        if (alreadySaved(currentUser, postToSaved))
            throw new ResourceAlreadyExistsException("Cannot saved post! because post is already been saved!");

        currentUser.getSavedPosts().add(postToSaved);
        postToSaved.getSavingUsers().add(currentUser);

        postRepository.save(postToSaved);
        userRepository.save(currentUser);
        log.debug("User with id of {} saved post successfully with id of {}", currentUser.getId(), postToSaved.getId());
        return postToSaved;
    }

    @Override
    public void unSavedPost(User currentUser, Post postToUnSave) {
        if (notSaved(currentUser, postToUnSave))
            throw new ResourceNotFoundException("Cannot unsaved post! because there's no saved post!");

        currentUser.getSavedPosts().remove(postToUnSave);
        postToUnSave.getSavingUsers().remove(currentUser);

        postRepository.save(postToUnSave);
        userRepository.save(currentUser);
        log.debug("User with id of {} unsaved post successfully with id of {}", currentUser.getId(), postToUnSave.getId());
    }

    @Override
    public Page<Post> getAllSavedPosts(User currentUser, Pageable pageable) {
        List<Post> posts = postRepository.findAllSavedPosts(currentUser, pageable).stream()
                .filter(Post::isActive)
                .toList();
        return PageableUtil.toPage(posts, pageable);
    }

    @Override
    public Post sharePost(User currentUser, Post postToShare) {
        if (postToShare.isInactive())
            throw new ResourceNotFoundException("Cannot share post! because post with id of " + postToShare.getId() + " does not exists or already deleted!") ;

        if (alreadyShare(currentUser, postToShare))
            throw new ResourceAlreadyExistsException("Cannot share post! because post already been shared!");

        currentUser.getSharedPosts().add(postToShare);
        postToShare.getSharers().add(currentUser);

        postRepository.save(postToShare);
        userRepository.save(currentUser);
        log.debug("User with id of {} shared post with id of {} successfully", currentUser.getId(), postToShare.getId());
        return postToShare;
    }

    @Override
    public void unSharePost(User currentUser, Post postToUnShare) {
        if (notShare(currentUser, postToUnShare))
            throw new ResourceNotFoundException("Cannot unshare post! because there's no share post!");

        currentUser.getSharedPosts().remove(postToUnShare);
        postToUnShare.getSharers().remove(currentUser);

        postRepository.save(postToUnShare);
        userRepository.save(currentUser);
        log.debug("User with id of {} shared post with id of {} successfully", currentUser.getId(), postToUnShare.getId());
    }

    @Override
    public Page<Post> getAllSharedPosts(User currentUser, Pageable pageable) {
        List<Post> posts = postRepository.findAllSharedPosts(currentUser, pageable).stream()
                .filter(Post::isActive)
                .toList();

        return PageableUtil.toPage(posts, pageable);
    }
}
