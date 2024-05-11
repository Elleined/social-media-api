package com.elleined.socialmediaapi.service.react;

import com.elleined.socialmediaapi.exception.BlockedException;
import com.elleined.socialmediaapi.exception.NotOwnedException;
import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.react.CommentReactionMapper;
import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.React;
import com.elleined.socialmediaapi.repository.react.CommentReactRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CommentReactionService implements ReactionService<Comment, CommentReact> {
    private final BlockService blockService;

    private final CommentReactRepository commentReactRepository;
    private final CommentReactionMapper commentReactionMapper;

    @Override
    public CommentReact getById(int id) throws ResourceNotFoundException {
        return commentReactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reaction with id of " + id + " doesn't exists!"));
    }

    @Override
    public CommentReact getByUserReaction(User currentUser, Comment comment) {
        return comment.getReactions().stream()
                .filter(commentReact -> commentReact.getRespondent().equals(currentUser))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<CommentReact> getAll(Comment comment) {
        return comment.getReactions().stream()
                .sorted(Comparator.comparing(React::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public List<CommentReact> getAllReactionByEmojiType(Comment comment, Emoji.Type type) {
        return comment.getReactions().stream()
                .filter(commentReact -> commentReact.getEmoji().getType().equals(type))
                .sorted(Comparator.comparing(React::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public CommentReact save(User currentUser, Comment comment, Emoji emoji) {
        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot react to this comment! because this might be already deleted or doesn't exists!");
        if (blockService.isBlockedBy(currentUser, comment.getCommenter()))
            throw new BlockedException("Cannot react to this comment! because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter()))
            throw new BlockedException("Cannot react to this comment! because this user block you already!");

        CommentReact commentReact = commentReactionMapper.toEntity(currentUser, comment, emoji);
        commentReactRepository.save(commentReact);
        log.debug("User with id of {} successfully reacted with id of {} in comment with id of {}", currentUser.getId(), emoji.getId(), comment.getId());
        return commentReact;
    }

    @Override
    public void update(User currentUser, Comment comment, CommentReact commentReact, Emoji emoji) {
        if (currentUser.notOwned(commentReact))
            throw new NotOwnedException("Cannot update react to this comment! because you don't own this reaction");
        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot update react to this comment! because this might be already deleted or doesn't exists!");
        if (blockService.isBlockedBy(currentUser, comment.getCommenter()))
            throw new BlockedException("Cannot update react to this comment! because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter()))
            throw new BlockedException("Cannot update react to this comment! because this user block you already!");

        commentReact.setEmoji(emoji);
        commentReactRepository.save(commentReact);
        log.debug("User with id of {} updated his/her reaction to comment with id of {} to emoji with id of {}", currentUser.getId(), comment.getId(), emoji.getId());
    }

    @Override
    public void delete(User currentUser, CommentReact commentReact) {
        if (currentUser.notOwned(commentReact))
            throw new NotOwnedException("Cannot delete this comment reaction! because you don't owned this reaction!");

        commentReactRepository.delete(commentReact);
        log.debug("Reaction with id of {} removed successfully", commentReact.getId());
    }
}
