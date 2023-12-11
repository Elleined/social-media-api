package com.elleined.forumapi.service.emoji;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.emoji.Emoji;

import java.util.List;

public interface EmojiService {

    List<Emoji> getAll(Post post);
    List<Emoji> getAll(Comment comment);
    List<Emoji> getAll(Reply reply);

    List<Emoji> getAllEmojiByType(Post post, Emoji.Type type);
    List<Emoji> getAllEmojiByType(Comment comment, Emoji.Type type);
    List<Emoji> getAllEmojiByType(Reply reply, Emoji.Type type);

    Emoji save(Post post, Emoji emoji);
    Emoji save(Comment comment, Emoji emoji);
    Emoji save(Reply reply, Emoji emoji);
}
