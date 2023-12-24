package com.elleined.forumapi.service.react;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.emoji.Emoji;
import com.elleined.forumapi.model.react.CommentReact;
import com.elleined.forumapi.model.react.PostReact;
import com.elleined.forumapi.model.react.ReplyReact;
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

    @Override
    public List<PostReact> getAll(User currentUser, Post post) {
        return null;
    }

    @Override
    public List<CommentReact> getAll(User currentUser, Comment comment) {
        return null;
    }

    @Override
    public List<ReplyReact> getAll(User currentUser, Reply reply) {
        return null;
    }

    @Override
    public List<PostReact> getAllReactionByEmojiType(User currentUser, Post post, Emoji.Type type) {
        return null;
    }

    @Override
    public List<CommentReact> getAllReactionByEmojiType(User currentUser, Comment comment, Emoji.Type type) {
        return null;
    }

    @Override
    public List<ReplyReact> getAllReactionByEmojiType(User currentUser, Reply reply, Emoji.Type type) {
        return null;
    }

    @Override
    public PostReact save(User currentUser, Post post, Emoji emoji) {
        return null;
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
