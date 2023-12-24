package com.elleined.forumapi.service.react;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.Reply;
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
    public List<PostReact> getAll(Post post) {
        return null;
    }

    @Override
    public List<CommentReact> getAll(Comment comment) {
        return null;
    }

    @Override
    public List<ReplyReact> getAll(Reply reply) {
        return null;
    }

    @Override
    public List<PostReact> getAllEmojiByType(Post post, Emoji.Type type) {
        return null;
    }

    @Override
    public List<CommentReact> getAllEmojiByType(Comment comment, Emoji.Type type) {
        return null;
    }

    @Override
    public List<ReplyReact> getAllEmojiByType(Reply reply, Emoji.Type type) {
        return null;
    }

    @Override
    public PostReact save(Post post, Emoji emoji) {
        return null;
    }

    @Override
    public CommentReact save(Comment comment, Emoji emoji) {
        return null;
    }

    @Override
    public ReplyReact save(Reply reply, Emoji emoji) {
        return null;
    }
}
