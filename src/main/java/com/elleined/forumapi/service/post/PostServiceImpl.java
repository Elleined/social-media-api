package com.elleined.forumapi.service.post;

import com.elleined.forumapi.exception.*;
import com.elleined.forumapi.mapper.PostMapper;
import com.elleined.forumapi.model.*;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.repository.CommentRepository;
import com.elleined.forumapi.repository.MentionRepository;
import com.elleined.forumapi.repository.PostRepository;
import com.elleined.forumapi.repository.UserRepository;
import com.elleined.forumapi.service.ModalTrackerService;
import com.elleined.forumapi.service.block.BlockService;
import com.elleined.forumapi.service.image.ImageUploader;
import com.elleined.forumapi.utils.DirectoryFolders;
import com.elleined.forumapi.validator.StringValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PostServiceImpl implements PostService {
    private final UserRepository userRepository;
    private final BlockService blockService;

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    private final CommentRepository commentRepository;

    private final ImageUploader imageUploader;

    private final ModalTrackerService modalTrackerService;

    private final MentionRepository mentionRepository;

    @Value("${cropTrade.img.directory}")
    private String cropTradeImgDirectory;

    @Override
    public Post save(User currentUser, String body, MultipartFile attachedPicture, Set<User> mentionedUsers)
            throws EmptyBodyException,
            BlockedException,
            ResourceNotFoundException,
            IOException {

        if (StringValidator.isNotValidBody(body))
            throw new EmptyBodyException("Body cannot be empty! Please provide text for your post to be posted!");

        Post post = postMapper.toEntity(body, currentUser, attachedPicture.getOriginalFilename());
        currentUser.getPosts().add(post);
        postRepository.save(post);

        imageUploader.upload(cropTradeImgDirectory + DirectoryFolders.POST_PICTURES_FOLDER, attachedPicture);
        if (mentionedUsers != null) mentionAll(currentUser, mentionedUsers, post);
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

        if (post.getCommentSectionStatus() == Post.CommentSectionStatus.OPEN) {
            post.setCommentSectionStatus(Post.CommentSectionStatus.CLOSED);
        } else {
            post.setCommentSectionStatus(Post.CommentSectionStatus.OPEN);
        }
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
        return postRepository.findAll()
                .stream()
                .filter(post -> post.getStatus() == Status.ACTIVE)
                .filter(post -> !blockService.isBlockedBy(currentUser, post.getAuthor()))
                .filter(post -> !blockService.isYouBeenBlockedBy(currentUser, post.getAuthor()))
                .sorted(Comparator.comparing(Post::getDateCreated).reversed())
                .toList();
    }

    @Override
    public Optional<Comment> getPinnedComment(Post post) throws ResourceNotFoundException {
        Comment pinnedComment = post.getPinnedComment();
        if (post.isDeleted())
            throw new ResourceNotFoundException("Post with id of " + post.getId() + " might already been deleted or does not exists anymore!");

        if (pinnedComment == null) return Optional.empty();
        return Optional.of( pinnedComment );
    }

    @Override
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

    @Override
    public void pin(User currentUser, Post post, Comment comment) throws NotOwnedException, ResourceNotFoundException {
        if (currentUser.notOwned(post)) throw new NotOwnedException("User with id of " + currentUser.getId() + " does not own post with id of " + post.getId() + " for him/her to pin a comment in this post!");
        if (post.doesNotHave(comment)) throw new NotOwnedException("Post with id of " + post.getId() + " doesn't have comment with id of " + comment.getId());
        if (comment.isDeleted()) throw new ResourceNotFoundException("Comment with id of " + comment.getId() + " you specify is already deleted or doesn't exist anymore!");

        post.setPinnedComment(comment);
        postRepository.save(post);
        log.debug("Author with id of {} pinned comment with id {} in his/her post with id of {}", post.getAuthor().getId(), comment.getId(), post.getId());
    }

    @Override
    public void unpin(Comment comment) {
        comment.getPost().setPinnedComment(null);
        commentRepository.save(comment);

        log.debug("Post pinned comment unpinned successfully");
    }

    @Override
    public PostMention mention(User mentioningUser, User mentionedUser, Post post) {
        if (post.isDeleted()) throw new ResourceNotFoundException("Cannot mention! The post with id of " + post.getId() + " you are trying to mention might already been deleted or does not exists!");
        if (blockService.isBlockedBy(mentioningUser, mentionedUser)) throw new BlockedException("Cannot mention! You blocked the mentioned user with id of !" + mentionedUser.getId());
        if (blockService.isYouBeenBlockedBy(mentioningUser, mentionedUser)) throw  new BlockedException("Cannot mention! Mentioned user with id of " + mentionedUser.getId() + " already blocked you");
        if (mentioningUser.equals(mentionedUser)) throw new MentionException("Cannot mention! You are trying to mention yourself which is not possible!");

        NotificationStatus notificationStatus = modalTrackerService.isModalOpen(mentionedUser.getId(), post.getId(), ModalTracker.Type.POST)
                ? NotificationStatus.READ
                : NotificationStatus.UNREAD;

        PostMention postMention = PostMention.postMentionBuilder()
                .mentioningUser(mentioningUser)
                .mentionedUser(mentionedUser)
                .createdAt(LocalDateTime.now())
                .notificationStatus(notificationStatus)
                .post(post)
                .build();

        mentioningUser.getSentPostMentions().add(postMention);
        mentionedUser.getReceivePostMentions().add(postMention);
        post.getMentions().add(postMention);
        mentionRepository.save(postMention);
        log.debug("User with id of {} mentioned user with id of {} in post with id of {}", mentioningUser.getId(), mentionedUser.getId(), post.getId());
        return postMention;
    }

    @Override
    public void mentionAll(User mentioningUser, Set<User> mentionedUsers, Post post) {
        mentionedUsers.forEach(mentionedUser -> mention(mentioningUser, mentionedUser, post));
    }

    @Override
    public Post savedPost(User currentUser, Post postToSaved) {
        if (postToSaved.isDeleted()) throw new ResourceNotFoundException("Post with id of " + postToSaved.getId() + " does not exists or already deleted!") ;

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
        return currentUser.getSavedPosts();
    }

    @Override
    public Post sharePost(User currentUser, Post postToShare) {
        if (postToShare.isDeleted()) throw new ResourceNotFoundException("Post with id of " + postToShare.getId() + " does not exists or already deleted!") ;

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
        return currentUser.getSharedPosts();
    }
}
