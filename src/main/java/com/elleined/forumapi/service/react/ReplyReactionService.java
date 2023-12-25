package com.elleined.forumapi.service.react;

import com.elleined.forumapi.exception.BlockedException;
import com.elleined.forumapi.exception.NotOwnedException;
import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.emoji.Emoji;
import com.elleined.forumapi.model.react.CommentReact;
import com.elleined.forumapi.model.react.ReplyReact;
import com.elleined.forumapi.repository.react.ReplyReactRepository;
import com.elleined.forumapi.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ReplyReactionService implements ReactionService<Reply, ReplyReact> {
    private final BlockService blockService;
    private final ReplyReactRepository replyReactRepository;

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
        return reply.getReactions();
    }

    @Override
    public List<ReplyReact> getAllReactionByEmojiType(Reply reply, Emoji.Type type) {
        return reply.getReactions().stream()
                .filter(replyReact -> replyReact.getEmoji().getType().equals(type))
                .toList();
    }

    @Override
    public ReplyReact save(User currentUser, Reply reply, Emoji emoji) {
        if (reply.isDeleted())
            throw new ResourceNotFoundException("Cannot react to this reply! because this might be already deleted or doesn't exists!");
        if (blockService.isBlockedBy(currentUser, reply.getReplier()))
            throw new BlockedException("Cannot react to this reply! because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, reply.getReplier()))
            throw new BlockedException("Cannot react to this reply! because this user block you already!");

        ReplyReact replyReact = ReplyReact.replyReactBuilder()
                .createdAt(LocalDateTime.now())
                .respondent(currentUser)
                .notificationStatus(NotificationStatus.UNREAD)
                .emoji(emoji)
                .reply(reply)
                .build();

        replyReactRepository.save(replyReact);
        log.debug("User with id of {} successfully reacted with id of {} in reply with id of {}", currentUser.getId(), emoji.getId(), reply.getId());
        return replyReact;
    }

    @Override
    public ReplyReact update(User currentUser, Reply reply, ReplyReact replyReact, Emoji emoji) {
        if (currentUser.notOwned(replyReact))
            throw new NotOwnedException("Cannot update react to this reply! because you don't own this reaction");
        if (reply.isDeleted())
            throw new ResourceNotFoundException("Cannot update react to this reply! because this might be already deleted or doesn't exists!");
        if (blockService.isBlockedBy(currentUser, reply.getReplier()))
            throw new BlockedException("Cannot update react to this reply! because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, reply.getReplier()))
            throw new BlockedException("Cannot update react to this reply! because this user block you already!");

        replyReact.setEmoji(emoji);
        replyReactRepository.save(replyReact);
        log.debug("User with id of {} updated his/her reaction to reply with id of {} to emoji with id of {}", currentUser.getId(), reply.getId(), emoji.getId());
        return replyReact;
    }

    @Override
    public void delete(Reply reply, ReplyReact replyReact) {
        replyReactRepository.delete(replyReact);
        log.debug("Reaction with id of {} removed successfully with post with id of {}", replyReact.getId(), reply.getId());
    }
}
