package com.elleined.forumapi.service.react;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.emoji.Emoji;
import com.elleined.forumapi.model.react.CommentReact;
import com.elleined.forumapi.model.react.PostReact;
import com.elleined.forumapi.model.react.ReplyReact;

import java.util.List;

public interface ReactionService {

    List<PostReact> getAll(Post post);
    List<CommentReact> getAll(Comment comment);
    List<ReplyReact> getAll(Reply reply);

    List<PostReact> getAllReactionByEmojiType(Post post, Emoji.Type type);
    List<CommentReact> getAllReactionByEmojiType(Comment comment, Emoji.Type type);
    List<ReplyReact> getAllReactionByEmojiType(Reply reply, Emoji.Type type);

    PostReact save(User currentUser, Post post, Emoji emoji);
    CommentReact save(User currentUser, Comment comment, Emoji emoji);
    ReplyReact save(User currentUser, Reply reply, Emoji emoji);

    PostReact update(User currentUser, PostReact postReact, Emoji emoji);
    CommentReact update(User currentUser, CommentReact commentReact, Emoji emoji);
    ReplyReact update(User currentUser, ReplyReact replyReact, Emoji emoji);

    void delete(Post post, PostReact postReact);
    void delete(Comment comment, CommentReact commentReact);
    void delete(Reply reply, ReplyReact replyReact);
}
