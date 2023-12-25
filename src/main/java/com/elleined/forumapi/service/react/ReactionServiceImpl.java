package com.elleined.forumapi.service.react;

import com.elleined.forumapi.exception.BlockedException;
import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.model.*;
import com.elleined.forumapi.model.emoji.Emoji;
import com.elleined.forumapi.model.react.CommentReact;
import com.elleined.forumapi.model.react.PostReact;
import com.elleined.forumapi.model.react.ReplyReact;
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
    private final PostReactRepository postReactRepository;
    private final CommentReactRepository commentReactRepository;
    private final ReplyReactRepository replyReactRepository;

    private final BlockService blockService;

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
        postReact.setEmoji(emoji);
        postReactRepository.save(postReact);
        log.debug("User with id of {} updated his/her reaction to post with id of {} to emoji with id of {}", currentUser.getId(), postReact.getPost().getId(), emoji.getId());
        return postReact;
    }

    @Override
    public CommentReact update(User currentUser, CommentReact commentReact, Emoji emoji) {
        commentReact.setEmoji(emoji);
        commentReactRepository.save(commentReact);
        log.debug("User with id of {} updated his/her reaction to comment with id of {} to emoji with id of {}", currentUser.getId(), commentReact.getComment().getId(), emoji.getId());
        return commentReact;
    }

    @Override
    public ReplyReact update(User currentUser, ReplyReact replyReact, Emoji emoji) {
        replyReact.setEmoji(emoji);
        replyReactRepository.save(replyReact);
        log.debug("User with id of {} updated his/her reaction to reply with id of {} to emoji with id of {}", currentUser.getId(), replyReact.getReply().getId(), emoji.getId());
        return replyReact;
    }

    @Override
    public PostReact delete(User currentUser, PostReact postReact) {
        return null;
    }

    @Override
    public CommentReact delete(User currentUser, CommentReact commentReact) {
        return null;
    }

    @Override
    public ReplyReact delete(User currentUser, ReplyReact replyReact) {
        return null;
    }
}
