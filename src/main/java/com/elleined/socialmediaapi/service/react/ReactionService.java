package com.elleined.socialmediaapi.service.react;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.Reaction;
import com.elleined.socialmediaapi.model.user.User;

import java.util.List;

public interface ReactionService {
    Reaction getById(int id) throws ResourceNotFoundException;

    Reaction save(User currentUser, Post post, Emoji emoji);
    Reaction save(User currentUser, Post post, Comment comment, Emoji emoji);
    Reaction save(User currentUser, Post post, Comment comment, Reply reply, Emoji emoji);

    void update(User currentUser, Post post, Reaction reaction, Emoji emoji);
    void update(User currentUser, Post post, Comment comment, Reaction reaction, Emoji emoji);
    void update(User currentUser, Post post, Comment comment, Reply reply, Reaction reaction, Emoji emoji);

    void delete(User currentUser, Post post, Reaction reaction);
    void delete(User currentUser, Post post, Comment comment, Reaction reaction);
    void delete(User currentUser, Post post, Comment comment, Reply reply, Reaction reaction);

    List<Reaction> getAll(User currentUser, Post post);
    List<Reaction> getAll(User currentUser, Post post, Comment comment);
    List<Reaction> getAll(User currentUser, Post post, Comment comment, Reply reply);

    boolean isAlreadyReactedTo(User currentUser, Post post);
    boolean isAlreadyReactedTo(User currentUser, Post post, Comment comment);
    boolean isAlreadyReactedTo(User currentUser, Post post, Comment comment, Reply reply);

    Reaction getByUserReaction(User currentUser, Post post);
    Reaction getByUserReaction(User currentUser, Post post, Comment comment);
    Reaction getByUserReaction(User currentUser, Post post, Comment comment, Reply reply);

    default List<Reaction> getAllByEmoji(User currentUser, Post post, Emoji emoji) {
        return this.getAll(currentUser, post).stream()
                .filter(react -> react.getEmoji().equals(emoji))
                .toList();
    }

    default List<Reaction> getAllByEmoji(User currentUser, Post post, Comment comment, Emoji emoji) {
        return this.getAll(currentUser, post, comment).stream()
                .filter(react -> react.getEmoji().equals(emoji))
                .toList();
    }

    default List<Reaction> getAllByEmoji(User currentUser, Post post, Comment comment, Reply reply, Emoji emoji) {
        return this.getAll(currentUser, post, comment, reply).stream()
                .filter(react -> react.getEmoji().equals(emoji))
                .toList();
    }
}


