package com.elleined.socialmediaapi.service.react;

import com.elleined.socialmediaapi.exception.BlockedException;
import com.elleined.socialmediaapi.exception.NotOwnedException;
import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.react.ReplyReactionMapper;
import com.elleined.socialmediaapi.model.main.Reply;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.React;
import com.elleined.socialmediaapi.repository.react.ReplyReactRepository;
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
public class ReplyReactionService implements ReactionService<Reply, ReplyReact> {
    private final BlockService blockService;

    private final ReplyReactRepository replyReactRepository;
    private final ReplyReactionMapper replyReactionMapper;

    @Override
    public ReplyReact getById(int id) throws ResourceNotFoundException {
        return replyReactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reaction with id of " + id + " doesn't exists!"));
    }

    @Override
    public ReplyReact getByUserReaction(User currentUser, Reply reply) {
        return reply.getReactions().stream()
                .filter(replyReact -> replyReact.getRespondent().equals(currentUser))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<ReplyReact> getAll(Reply reply) {
        return reply.getReactions().stream()
                .sorted(Comparator.comparing(React::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public List<ReplyReact> getAllReactionByEmojiType(Reply reply, Emoji.Type type) {
        return reply.getReactions().stream()
                .filter(replyReact -> replyReact.getEmoji().getType().equals(type))
                .sorted(Comparator.comparing(React::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public ReplyReact save(User currentUser, Reply reply, Emoji emoji) {
        if (reply.isInactive())
            throw new ResourceNotFoundException("Cannot react to this reply! because this might be already deleted or doesn't exists!");
        if (blockService.isBlockedBy(currentUser, reply.getReplier()))
            throw new BlockedException("Cannot react to this reply! because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, reply.getReplier()))
            throw new BlockedException("Cannot react to this reply! because this user block you already!");

        ReplyReact replyReact = replyReactionMapper.toEntity(currentUser, reply, emoji);
        replyReactRepository.save(replyReact);
        log.debug("User with id of {} successfully reacted with id of {} in reply with id of {}", currentUser.getId(), emoji.getId(), reply.getId());
        return replyReact;
    }

    @Override
    public void update(User currentUser, Reply reply, ReplyReact replyReact, Emoji emoji) {
        if (currentUser.notOwned(replyReact))
            throw new NotOwnedException("Cannot update react to this reply! because you don't own this reaction");
        if (reply.isInactive())
            throw new ResourceNotFoundException("Cannot update react to this reply! because this might be already deleted or doesn't exists!");
        if (blockService.isBlockedBy(currentUser, reply.getReplier()))
            throw new BlockedException("Cannot update react to this reply! because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, reply.getReplier()))
            throw new BlockedException("Cannot update react to this reply! because this user block you already!");

        replyReact.setEmoji(emoji);
        replyReactRepository.save(replyReact);
        log.debug("User with id of {} updated his/her reaction to reply with id of {} to emoji with id of {}", currentUser.getId(), reply.getId(), emoji.getId());
    }

    @Override
    public void delete(User currentUser, ReplyReact replyReact) {
        if (currentUser.notOwned(replyReact))
            throw new NotOwnedException("Cannot delete this reply reaction! because you don't owned this reaction!");
        replyReactRepository.delete(replyReact);
        log.debug("Reaction with id of {} removed successfully", replyReact.getId());
    }
}
