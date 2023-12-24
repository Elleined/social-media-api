package com.elleined.forumapi.service.react;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.emoji.Emoji;
import com.elleined.forumapi.model.react.CommentReact;
import com.elleined.forumapi.model.react.PostReact;
import com.elleined.forumapi.model.react.ReplyReact;

import java.util.List;

public interface ReactionService {

    List<PostReact> getAll(Post post);
    List<CommentReact> getAll(Comment comment);
    List<ReplyReact> getAll(Reply reply);

    List<PostReact> getAllEmojiByType(Post post, Emoji.Type type);
    List<CommentReact> getAllEmojiByType(Comment comment, Emoji.Type type);
    List<ReplyReact> getAllEmojiByType(Reply reply, Emoji.Type type);

    PostReact save(Post post, Emoji emoji);
    CommentReact save(Comment comment, Emoji emoji);
    ReplyReact save(Reply reply, Emoji emoji);
}
