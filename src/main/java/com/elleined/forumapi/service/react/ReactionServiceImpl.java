package com.elleined.forumapi.service.react;

import com.elleined.forumapi.exception.BlockedException;
import com.elleined.forumapi.exception.NotOwnedException;
import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.model.*;
import com.elleined.forumapi.model.emoji.Emoji;
import com.elleined.forumapi.model.react.CommentReact;
import com.elleined.forumapi.model.react.PostReact;
import com.elleined.forumapi.model.react.ReplyReact;
import com.elleined.forumapi.repository.CommentRepository;
import com.elleined.forumapi.repository.PostRepository;
import com.elleined.forumapi.repository.ReplyRepository;
import com.elleined.forumapi.repository.react.CommentReactRepository;
import com.elleined.forumapi.repository.react.PostReactRepository;
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
public class ReactionServiceImpl implements ReactionService {
    private final BlockService blockService;

    private final PostReactRepository postReactRepository;
    private final CommentReactRepository commentReactRepository;
    private final ReplyReactRepository replyReactRepository;

    @Override
    public List<PostReact> getAll(Post post) {
        return post.getReactions();
    }

    @Override
    public List<CommentReact> getAll(Comment comment) {
        return comment.getReactions();
    }

    @Override
    public List<ReplyReact> getAll(Reply reply) {
        return reply.getReactions();
    }

    @Override
    public List<PostReact> getAllReactionByEmojiType(Post post, Emoji.Type type) {
        return post.getReactions().stream()
                .filter(postReact -> postReact.getEmoji().getType().equals(type))
                .toList();
    }

    @Override
    public List<CommentReact> getAllReactionByEmojiType(Comment comment, Emoji.Type type) {
        return comment.getReactions().stream()
                .filter(commentReact -> commentReact.getEmoji().getType().equals(type))
                .toList();
    }

    @Override
    public List<ReplyReact> getAllReactionByEmojiType(Reply reply, Emoji.Type type) {
        return reply.getReactions().stream()
                .filter(replyReact -> replyReact.getEmoji().getType().equals(type))
                .toList();
    }

    @Override
    public PostReact save(User currentUser, Post post, Emoji emoji) {
        if (post.isDeleted())
            throw new ResourceNotFoundException("Cannot react to this post! because this might be already deleted or doesn't exists!");
        if (blockService.isBlockedBy(currentUser, post.getAuthor()))
            throw new BlockedException("Cannot react to this post! because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, post.getAuthor()))
            throw new BlockedException("Cannot react to this post! because this user block you already!");

        PostReact postReact = PostReact.postReactBuilder()
                .createdAt(LocalDateTime.now())
                .respondent(currentUser)
                .notificationStatus(NotificationStatus.UNREAD)
                .emoji(emoji)
                .post(post)
                .build();

        postReactRepository.save(postReact);
        log.debug("User with id of {} successfully reacted with id of {} in post with id of {}", currentUser.getId(), emoji.getId(), post.getId());
        return postReact;
    }

    @Override
    public CommentReact save(User currentUser, Comment comment, Emoji emoji) {
        if (comment.isDeleted())
            throw new ResourceNotFoundException("Cannot react to this comment! because this might be already deleted or doesn't exists!");
        if (blockService.isBlockedBy(currentUser, comment.getCommenter()))
            throw new BlockedException("Cannot react to this comment! because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter()))
            throw new BlockedException("Cannot react to this comment! because this user block you already!");

        CommentReact commentReact = CommentReact.commentReactBuilder()
                .createdAt(LocalDateTime.now())
                .respondent(currentUser)
                .notificationStatus(NotificationStatus.UNREAD)
                .emoji(emoji)
                .comment(comment)
                .build();

        commentReactRepository.save(commentReact);
        log.debug("User with id of {} successfully reacted with id of {} in comment with id of {}", currentUser.getId(), emoji.getId(), comment.getId());
        return commentReact;
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
    public PostReact update(User currentUser, PostReact postReact, Emoji emoji) {
        Post post = postReact.getPost();
        if (currentUser.notOwned(postReact))
            throw new NotOwnedException("Cannot update react to this post! because you don't own this reaction");
        if (post.isDeleted())
            throw new ResourceNotFoundException("Cannot update react to this post! because this might be already deleted or doesn't exists!");
        if (blockService.isBlockedBy(currentUser, post.getAuthor()))
            throw new BlockedException("Cannot update react to this post! because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, post.getAuthor()))
            throw new BlockedException("Cannot update react to this post! because this user block you already!");

        postReact.setEmoji(emoji);
        postReactRepository.save(postReact);
        log.debug("User with id of {} updated his/her reaction to post with id of {} to emoji with id of {}", currentUser.getId(), post.getId(), emoji.getId());
        return postReact;
    }

    @Override
    public CommentReact update(User currentUser, CommentReact commentReact, Emoji emoji) {
        Comment comment = commentReact.getComment();
        if (currentUser.notOwned(commentReact))
            throw new NotOwnedException("Cannot update react to this comment! because you don't own this reaction");
        if (comment.isDeleted())
            throw new ResourceNotFoundException("Cannot update react to this comment! because this might be already deleted or doesn't exists!");
        if (blockService.isBlockedBy(currentUser, comment.getCommenter()))
            throw new BlockedException("Cannot update react to this comment! because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter()))
            throw new BlockedException("Cannot update react to this comment! because this user block you already!");

        commentReact.setEmoji(emoji);
        commentReactRepository.save(commentReact);
        log.debug("User with id of {} updated his/her reaction to comment with id of {} to emoji with id of {}", currentUser.getId(), comment.getId(), emoji.getId());
        return commentReact;
    }

    @Override
    public ReplyReact update(User currentUser, ReplyReact replyReact, Emoji emoji) {
        Reply reply = replyReact.getReply();
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
    public void delete(Post post, PostReact postReact) {
        postReactRepository.delete(postReact);
        log.debug("Reaction with id of {} removed successfully with post with id of {}", postReact.getId(), post.getId());
    }

    @Override
    public void delete(Comment comment, CommentReact commentReact) {
        commentReactRepository.delete(commentReact);
        log.debug("Reaction with id of {} removed successfully with comment with id of {}", commentReact.getId(), comment.getId());
    }

    @Override
    public void delete(Reply reply, ReplyReact replyReact) {
        replyReactRepository.delete(replyReact);
        log.debug("Reaction with id of {} removed successfully with post with id of {}", replyReact.getId(), reply.getId());
    }
}
