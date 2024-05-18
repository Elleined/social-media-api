package com.elleined.socialmediaapi.service.react;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.React;
import com.elleined.socialmediaapi.model.user.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface ReactionService {
    React getById(int id) throws ResourceNotFoundException;

    React save(User currentUser, Post post, Emoji emoji);
    React save(User currentUser, Post post, Comment comment, Emoji emoji);
    React save(User currentUser, Post post, Comment comment, Reply reply, Emoji emoji);

    React update(User currentUser, Post post, React react, Emoji emoji);
    React update(User currentUser, Post post, Comment comment, React react, Emoji emoji);
    React update(User currentUser, Post post, Comment comment, Reply reply, React react, Emoji emoji);

    void delete(User currentUser, Post post, React react, Emoji emoji);
    void delete(User currentUser, Post post, Comment comment, React react, Emoji emoji);
    void delete(User currentUser, Post post, Comment comment, Reply reply, React react, Emoji emoji);

    List<React> getAll(User currentUser, Post post);
    List<React> getAll(User currentUser, Post post, Comment comment);
    List<React> getAll(User currentUser, Post post, Comment comment, Reply reply);

    boolean isAlreadyReactedTo(User currentUser, Post post);
    boolean isAlreadyReactedTo(User currentUser, Post post, Comment comment);
    boolean isAlreadyReactedTo(User currentUser, Post post, Comment comment, Reply reply);

    default List<React> getAllByEmoji(User currentUser, Post post, Emoji emoji) {
        return this.getAll(currentUser, post).stream()
                .filter(react -> react.getEmoji().equals(emoji))
                .toList();
    }

    default List<React> getAllByEmoji(User currentUser, Post post, Comment comment, Emoji emoji) {
        return this.getAll(currentUser, post, comment).stream()
                .filter(react -> react.getEmoji().equals(emoji))
                .toList();
    }

    default List<React> getAllByEmoji(User currentUser, Post post, Comment comment, Reply reply, Emoji emoji) {
        return this.getAll(currentUser, post, comment, reply).stream()
                .filter(react -> react.getEmoji().equals(emoji))
                .toList();
    }
}


