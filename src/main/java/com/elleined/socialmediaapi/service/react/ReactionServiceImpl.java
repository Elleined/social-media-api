package com.elleined.socialmediaapi.service.react;

import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.mapper.react.ReactionMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.React;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.react.ReactionRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.main.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final BlockService blockService;

    private final PostService postService;
    private final CommentService commentService;
    private final ReplyService replyService;

    private final ReactionRepository reactionRepository;
    private final ReactionMapper reactionMapper;

    @Override
    public React getById(int id) throws ResourceNotFoundException {
        return reactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reaction with id of " + id + " doesn't exists!"));
    }

    @Override
    public React save(User currentUser, Post post, Emoji emoji) {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot react to this post! because post might be already deleted or doesn't exists!");

        if (blockService.isBlockedBy(currentUser, post.getCreator()))
            throw new BlockedException("Cannot react to this post! because you blocked this user already!");

        if (blockService.isYouBeenBlockedBy(currentUser, post.getCreator()))
            throw new BlockedException("Cannot react to this post! because this user block you already!");

        React react = reactionMapper.toEntity(currentUser, emoji);
        reactionRepository.save(react);

        post.getReactions().add(react);
        postService.save(post);
        log.debug("Reacting to post with id of {} success with emoji id of {}", post.getId(), emoji.getId());
        return react;
    }

    @Override
    public React save(User currentUser, Post post, Comment comment, Emoji emoji) {
        if (post.notOwned(comment))
            throw new ResourceNotOwnedException("Cannot react to this comment! because post doesn't owned this comment!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot react to this comment! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot react to this comment! because comment might be already deleted or doesn't exists!");

        if (blockService.isBlockedBy(currentUser, comment.getCreator()))
            throw new BlockedException("Cannot react to this comment! because you blocked this user already!");

        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCreator()))
            throw new BlockedException("Cannot react to this comment! because this user block you already!");

        React react = reactionMapper.toEntity(currentUser, emoji);
        reactionRepository.save(react);

        comment.getReactions().add(react);
        commentService.save(comment);
        log.debug("Reacting to comment with id of {} success with emoji id of {}", comment.getId(), emoji.getId());
        return react;
    }

    @Override
    public React save(User currentUser, Post post, Comment comment, Reply reply, Emoji emoji) {
        if (post.notOwned(comment))
            throw new ResourceNotOwnedException("Cannot react to this reply! because post doesn't owned this comment!");

        if (comment.notOwned(reply))
            throw new ResourceNotOwnedException("Cannot react to this reply! because comment doesn't owned this reply!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot react to this reply! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot react to this reply! because comment might be already deleted or doesn't exists!");

        if (reply.isInactive())
            throw new ResourceNotFoundException("Cannot react to this reply! because reply might be already deleted or doesn't exists!");

        if (blockService.isBlockedBy(currentUser, comment.getCreator()))
            throw new BlockedException("Cannot react to this reply! because you blocked this user already!");

        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCreator()))
            throw new BlockedException("Cannot react to this reply! because this user block you already!");

        React react = reactionMapper.toEntity(currentUser, emoji);
        reactionRepository.save(react);

        reply.getReactions().add(react);
        replyService.save(reply);
        log.debug("Reacting to reply with id of {} success with emoji id of {}", reply.getId(), emoji.getId());
        return react;
    }

    @Override
    public React update(User currentUser, Post post, React react, Emoji emoji) {
        if (currentUser.notOwned(post))
            throw new ResourceNotOwnedException("Cannot update reaction to this post! because current user doesn't owned this post!");

        if (currentUser.notOwned(react))
            throw new ResourceNotOwnedException("Cannot update reaction to this post! because current user doesn't owned this reaction!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot update reaction to this post! because post might be already deleted or doesn't exists!");

        react.setEmoji(emoji);
        reactionRepository.save(react);
        log.debug("Updating current user reaction to post with id of {} success with emoji id of {}", post.getId(), emoji.getId());
        return react;
    }

    @Override
    public React update(User currentUser, Post post, Comment comment, React react, Emoji emoji) {
        if (currentUser.notOwned(comment))
            throw new ResourceNotOwnedException("Cannot update reaction this comment! because current user doesn't owned this comment!");

        if (currentUser.notOwned(react))
            throw new ResourceNotOwnedException("Cannot update reaction this comment! because current user doesn't owned this reaction!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot update reaction this comment! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot update reaction this comment! because comment might be already deleted or doesn't exists!");

        react.setEmoji(emoji);
        reactionRepository.save(react);
        log.debug("Updating current user reaction to comment with id of {} success with emoji id of {}", comment.getId(), emoji.getId());
        return react;
    }

    @Override
    public React update(User currentUser, Post post, Comment comment, Reply reply, React react, Emoji emoji) {
        if (currentUser.notOwned(reply))
            throw new ResourceNotOwnedException("Cannot update reaction to this reply! because current user doesn't owned this reply!");

        if (currentUser.notOwned(react))
            throw new ResourceNotOwnedException("Cannot update reaction to this reply! because current user doesn't owned this reaction!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot update reaction to this reply! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot update reaction to this reply! because comment might be already deleted or doesn't exists!");

        if (reply.isInactive())
            throw new ResourceNotFoundException("Cannot update reaction to this reply! because reply might be already deleted or doesn't exists!");

        react.setEmoji(emoji);
        reactionRepository.save(react);
        log.debug("Updating current user reaction to reply with id of {} success with emoji id of {}", reply.getId(), emoji.getId());
        return react;
    }

    @Override
    public void delete(User currentUser, Post post, React react, Emoji emoji) {
        if (currentUser.notOwned(post))
            throw new ResourceNotOwnedException("Cannot delete reaction to this post! because current user doesn't owned this post!");

        if (currentUser.notOwned(react))
            throw new ResourceNotOwnedException("Cannot delete reaction to this post! because current user doesn't owned this reaction!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot delete reaction to this post! because post might be already deleted or doesn't exists!");

        reactionRepository.delete(react);
        log.debug("Deleting reaction to post with id of {} success!", post.getId());
    }

    @Override
    public void delete(User currentUser, Post post, Comment comment, React react, Emoji emoji) {
        if (currentUser.notOwned(comment))
            throw new ResourceNotOwnedException("Cannot delete reaction this comment! because current user doesn't owned this comment!");

        if (currentUser.notOwned(react))
            throw new ResourceNotOwnedException("Cannot delete reaction this comment! because current user doesn't owned this reaction!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot delete reaction this comment! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot delete reaction this comment! because comment might be already deleted or doesn't exists!");

        reactionRepository.delete(react);
        log.debug("Deleting reaction to comment with id of {} success!", comment.getId());
    }

    @Override
    public void delete(User currentUser, Post post, Comment comment, Reply reply, React react, Emoji emoji) {
        if (currentUser.notOwned(reply))
            throw new ResourceNotOwnedException("Cannot delete reaction to this reply! because current user doesn't owned this reply!");

        if (currentUser.notOwned(react))
            throw new ResourceNotOwnedException("Cannot delete reaction to this reply! because current user doesn't owned this reaction!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot delete reaction to this reply! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot delete reaction to this reply! because comment might be already deleted or doesn't exists!");

        if (reply.isInactive())
            throw new ResourceNotFoundException("Cannot delete reaction to this reply! because reply might be already deleted or doesn't exists!");

        reactionRepository.delete(react);
        log.debug("Deleting reaction to reply with id of {} success!", reply.getId());
    }


    @Override
    public List<React> getAll(User currentUser, Post post) {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot retrieve reactions to this post! because this might be already deleted or doesn't exists!");

        return post.getReactions().stream()
                .sorted(Comparator.comparing(React::getCreatedAt).reversed())
                .toList();

    }

    @Override
    public List<React> getAll(User currentUser, Post post, Comment comment) {
        if (post.notOwned(comment))
            throw new ResourceNotOwnedException("Cannot get all reactions to this comment! because post doesn't have this comment!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot get all reactions to this comment! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot get all reactions to this comment! because comment might be already deleted or doesn't exists!");

        return comment.getReactions().stream()
                .sorted(Comparator.comparing(React::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public List<React> getAll(User currentUser, Post post, Comment comment, Reply reply) {
        if (post.notOwned(comment))
            throw new ResourceNotOwnedException("Current user doesn't owned this comment!");

        if (comment.notOwned(reply))
            throw new ResourceNotOwnedException("Current user doesn't owned this reply!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot get all reactions to this reply! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot get all reactions to this reply! because comment might be already deleted or doesn't exists!");

        if (reply.isInactive())
            throw new ResourceNotFoundException("Cannot get all reactions to this reply! because reply might be already deleted or doesn't exists!");

        return reply.getReactions().stream()
                .sorted(Comparator.comparing(React::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public boolean isAlreadyReactedTo(User currentUser, Post post) {
        return post.getReactions().stream()
                .map(React::getCreator)
                .anyMatch(currentUser::equals);
    }

    @Override
    public boolean isAlreadyReactedTo(User currentUser, Post post, Comment comment) {
        return comment.getReactions().stream()
                .map(React::getCreator)
                .anyMatch(currentUser::equals);
    }

    @Override
    public boolean isAlreadyReactedTo(User currentUser, Post post, Comment comment, Reply reply) {
        return reply.getReactions().stream()
                .map(React::getCreator)
                .anyMatch(currentUser::equals);
    }
}
