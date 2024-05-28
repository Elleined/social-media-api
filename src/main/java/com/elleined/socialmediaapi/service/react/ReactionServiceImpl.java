package com.elleined.socialmediaapi.service.react;

import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.mapper.react.ReactionMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.CommentRepository;
import com.elleined.socialmediaapi.repository.main.PostRepository;
import com.elleined.socialmediaapi.repository.main.ReplyRepository;
import com.elleined.socialmediaapi.repository.react.ReactionRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.comment.CommentServiceRestriction;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.main.post.PostServiceRestriction;
import com.elleined.socialmediaapi.service.main.reply.ReplyService;
import com.elleined.socialmediaapi.service.user.UserServiceRestriction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    private final BlockService blockService;

    private final PostService postService;
    private final CommentService commentService;
    private final ReplyService replyService;

    private final ReactionRepository reactionRepository;
    private final ReactionMapper reactionMapper;

    private final UserServiceRestriction userServiceRestriction;
    private final PostServiceRestriction postServiceRestriction;
    private final CommentServiceRestriction commentServiceRestriction;

    @Override
    public Reaction getById(int id) throws ResourceNotFoundException {
        return reactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reaction with id of " + id + " doesn't exists!"));
    }

    @Override
    public Reaction save(User currentUser, Post post, Emoji emoji) {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot react to this post! because post might be already deleted or doesn't exists!");

        if (blockService.isBlockedByYou(currentUser, post.getCreator()))
            throw new BlockedException("Cannot react to this post! because you blocked this user already!");

        if (blockService.isYouBeenBlockedBy(currentUser, post.getCreator()))
            throw new BlockedException("Cannot react to this post! because this user block you already!");

        Reaction reaction = reactionMapper.toEntity(currentUser, emoji);
        reactionRepository.save(reaction);

        post.getReactions().add(reaction);
        postService.save(post);
        log.debug("Reacting to post with id of {} success with emoji id of {}", post.getId(), emoji.getId());
        return reaction;
    }

    @Override
    public Reaction save(User currentUser, Post post, Comment comment, Emoji emoji) {
        if (postServiceRestriction.notOwned(post, comment))
            throw new ResourceNotOwnedException("Cannot react to this comment! because post doesn't owned this comment!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot react to this comment! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot react to this comment! because comment might be already deleted or doesn't exists!");

        if (blockService.isBlockedByYou(currentUser, comment.getCreator()))
            throw new BlockedException("Cannot react to this comment! because you blocked this user already!");

        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCreator()))
            throw new BlockedException("Cannot react to this comment! because this user block you already!");

        Reaction reaction = reactionMapper.toEntity(currentUser, emoji);
        reactionRepository.save(reaction);

        comment.getReactions().add(reaction);
        commentService.save(comment);
        log.debug("Reacting to comment with id of {} success with emoji id of {}", comment.getId(), emoji.getId());
        return reaction;
    }

    @Override
    public Reaction save(User currentUser, Post post, Comment comment, Reply reply, Emoji emoji) {
        if (postServiceRestriction.notOwned(post, comment))
            throw new ResourceNotOwnedException("Cannot react to this reply! because post doesn't owned this comment!");

        if (commentServiceRestriction.notOwned(comment, reply))
            throw new ResourceNotOwnedException("Cannot react to this reply! because comment doesn't owned this reply!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot react to this reply! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot react to this reply! because comment might be already deleted or doesn't exists!");

        if (reply.isInactive())
            throw new ResourceNotFoundException("Cannot react to this reply! because reply might be already deleted or doesn't exists!");

        if (blockService.isBlockedByYou(currentUser, comment.getCreator()))
            throw new BlockedException("Cannot react to this reply! because you blocked this user already!");

        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCreator()))
            throw new BlockedException("Cannot react to this reply! because this user block you already!");

        Reaction reaction = reactionMapper.toEntity(currentUser, emoji);
        reactionRepository.save(reaction);

        reply.getReactions().add(reaction);
        replyService.save(reply);
        log.debug("Reacting to reply with id of {} success with emoji id of {}", reply.getId(), emoji.getId());
        return reaction;
    }

    @Override
    public void update(User currentUser, Post post, Reaction reaction, Emoji emoji) {
        if (userServiceRestriction.notOwned(currentUser, reaction))
            throw new ResourceNotOwnedException("Cannot update reaction to this post! because current user doesn't owned this reaction!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot update reaction to this post! because post might be already deleted or doesn't exists!");

        reaction.setEmoji(emoji);
        reaction.setUpdatedAt(LocalDateTime.now());
        reactionRepository.save(reaction);
        log.debug("Updating current user reaction to post with id of {} success with emoji id of {}", post.getId(), emoji.getId());
    }

    @Override
    public void update(User currentUser, Post post, Comment comment, Reaction reaction, Emoji emoji) {
        if (userServiceRestriction.notOwned(currentUser, reaction))
            throw new ResourceNotOwnedException("Cannot update reaction this comment! because current user doesn't owned this reaction!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot update reaction this comment! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot update reaction this comment! because comment might be already deleted or doesn't exists!");

        reaction.setEmoji(emoji);
        reaction.setUpdatedAt(LocalDateTime.now());

        reactionRepository.save(reaction);
        log.debug("Updating current user reaction to comment with id of {} success with emoji id of {}", comment.getId(), emoji.getId());
    }

    @Override
    public void update(User currentUser, Post post, Comment comment, Reply reply, Reaction reaction, Emoji emoji) {
        if (userServiceRestriction.notOwned(currentUser, reaction))
            throw new ResourceNotOwnedException("Cannot update reaction to this reply! because current user doesn't owned this reaction!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot update reaction to this reply! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot update reaction to this reply! because comment might be already deleted or doesn't exists!");

        if (reply.isInactive())
            throw new ResourceNotFoundException("Cannot update reaction to this reply! because reply might be already deleted or doesn't exists!");

        reaction.setEmoji(emoji);
        reaction.setUpdatedAt(LocalDateTime.now());

        reactionRepository.save(reaction);
        log.debug("Updating current user reaction to reply with id of {} success with emoji id of {}", reply.getId(), emoji.getId());
    }

    @Override
    public void delete(User currentUser, Post post, Reaction reaction) {
        if (userServiceRestriction.notOwned(currentUser, reaction))
            throw new ResourceNotOwnedException("Cannot delete reaction to this post! because current user doesn't owned this reaction!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot delete reaction to this post! because post might be already deleted or doesn't exists!");

        reactionRepository.delete(reaction);
        log.debug("Deleting reaction to post with id of {} success!", post.getId());
    }

    @Override
    public void delete(User currentUser, Post post, Comment comment, Reaction reaction) {
        if (userServiceRestriction.notOwned(currentUser, reaction))
            throw new ResourceNotOwnedException("Cannot delete reaction this comment! because current user doesn't owned this reaction!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot delete reaction this comment! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot delete reaction this comment! because comment might be already deleted or doesn't exists!");

        reactionRepository.delete(reaction);
        log.debug("Deleting reaction to comment with id of {} success!", comment.getId());
    }

    @Override
    public void delete(User currentUser, Post post, Comment comment, Reply reply, Reaction reaction) {
        if (userServiceRestriction.notOwned(currentUser, reaction))
            throw new ResourceNotOwnedException("Cannot delete reaction to this reply! because current user doesn't owned this reaction!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot delete reaction to this reply! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot delete reaction to this reply! because comment might be already deleted or doesn't exists!");

        if (reply.isInactive())
            throw new ResourceNotFoundException("Cannot delete reaction to this reply! because reply might be already deleted or doesn't exists!");

        reactionRepository.delete(reaction);
        log.debug("Deleting reaction to reply with id of {} success!", reply.getId());
    }


    @Override
    public List<Reaction> getAll(User currentUser, Post post, Pageable pageable) {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot retrieve reactions to this post! because this might be already deleted or doesn't exists!");

        return postRepository.findAllReactions(post, pageable).getContent();
    }

    @Override
    public List<Reaction> getAll(User currentUser, Post post, Comment comment, Pageable pageable) {
        if (postServiceRestriction.notOwned(post, comment))
            throw new ResourceNotOwnedException("Cannot get all reactions to this comment! because post doesn't have this comment!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot get all reactions to this comment! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot get all reactions to this comment! because comment might be already deleted or doesn't exists!");

        return commentRepository.findAllReactions(comment, pageable).getContent();
    }

    @Override
    public List<Reaction> getAll(User currentUser, Post post, Comment comment, Reply reply, Pageable pageable) {
        if (postServiceRestriction.notOwned(post, comment))
            throw new ResourceNotOwnedException("Current user doesn't owned this comment!");

        if (commentServiceRestriction.notOwned(comment, reply))
            throw new ResourceNotOwnedException("Current user doesn't owned this reply!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot get all reactions to this reply! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot get all reactions to this reply! because comment might be already deleted or doesn't exists!");

        if (reply.isInactive())
            throw new ResourceNotFoundException("Cannot get all reactions to this reply! because reply might be already deleted or doesn't exists!");

        return replyRepository.findAllReactions(reply, pageable).getContent();
    }

    @Override
    public boolean isAlreadyReactedTo(User currentUser, Post post) {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot retrieve user already reacted to this post! because this might be already deleted or doesn't exists!");

        return post.getReactions().stream()
                .map(Reaction::getCreator)
                .anyMatch(currentUser::equals);
    }

    @Override
    public boolean isAlreadyReactedTo(User currentUser, Post post, Comment comment) {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot retrieve user already reacted to this comment! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot retrieve user already reacted to this comment! because comment might be already deleted or doesn't exists!");

        return comment.getReactions().stream()
                .map(Reaction::getCreator)
                .anyMatch(currentUser::equals);
    }

    @Override
    public boolean isAlreadyReactedTo(User currentUser, Post post, Comment comment, Reply reply) {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot retrieve user already reacted to this reply! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot retrieve user already reacted to this reply! because comment might be already deleted or doesn't exists!");

        if (reply.isInactive())
            throw new ResourceNotFoundException("Cannot retrieve user already reacted to this reply! because reply might be already deleted or doesn't exists!");

        return reply.getReactions().stream()
                .map(Reaction::getCreator)
                .anyMatch(currentUser::equals);
    }

    @Override
    public Reaction getByUserReaction(User currentUser, Post post) {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot retrieve react by user to this post! because this might be already deleted or doesn't exists!");

        return post.getReactions().stream()
                .filter(react -> react.getCreator().equals(currentUser))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Getting reaction by user failed! "));
    }

    @Override
    public Reaction getByUserReaction(User currentUser, Post post, Comment comment) {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot retrieve react by user to this comment! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot retrieve react by user to this comment! because comment might be already deleted or doesn't exists!");

        return comment.getReactions().stream()
                .filter(react -> react.getCreator().equals(currentUser))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Getting reaction by user failed! "));
    }

    @Override
    public Reaction getByUserReaction(User currentUser, Post post, Comment comment, Reply reply) {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot retrieve react by user to this reply! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot retrieve react by user to this reply! because comment might be already deleted or doesn't exists!");

        if (reply.isInactive())
            throw new ResourceNotFoundException("Cannot retrieve react by user to this reply! because reply might be already deleted or doesn't exists!");

        return reply.getReactions().stream()
                .filter(react -> react.getCreator().equals(currentUser))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Getting reaction by user failed! "));
    }
}
