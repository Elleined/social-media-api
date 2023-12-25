package com.elleined.forumapi.service.react;

import com.elleined.forumapi.exception.NotOwnedException;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.emoji.Emoji;
import com.elleined.forumapi.model.react.CommentReact;
import com.elleined.forumapi.model.react.PostReact;
import com.elleined.forumapi.model.react.React;
import com.elleined.forumapi.model.react.ReplyReact;
import com.elleined.forumapi.repository.react.CommentReactRepository;
import com.elleined.forumapi.repository.react.PostReactRepository;
import com.elleined.forumapi.repository.react.ReactRepository;
import com.elleined.forumapi.repository.react.ReplyReactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final ReactRepository repository;
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
        React react = PostReact.postEmojiBuilder().build();
    }

    @Override
    public CommentReact save(User currentUser, Comment comment, Emoji emoji) {
        return null;
    }

    @Override
    public ReplyReact save(User currentUser, Reply reply, Emoji emoji) {
        return null;
    }
}
