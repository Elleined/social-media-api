package com.elleined.socialmediaapi.service.reaction;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.reaction.Emoji;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReactionService {
    Reaction getById(int id) throws ResourceNotFoundException;

    Reaction save(User currentUser, Post post, Emoji emoji);
    Reaction save(User currentUser, Post post, Comment comment, Emoji emoji);
    Reaction save(User currentUser, Post post, Comment comment, Reply reply, Emoji emoji);
    Reaction save(User currentUser, Story story, Emoji emoji);
    Reaction save(User currentUser, Note note, Emoji emoji);

    void update(User currentUser, Post post, Reaction reaction, Emoji emoji);
    void update(User currentUser, Post post, Comment comment, Reaction reaction, Emoji emoji);
    void update(User currentUser, Post post, Comment comment, Reply reply, Reaction reaction, Emoji emoji);
    void update(User currentUser, Story story, Reaction reaction, Emoji emoji);
    void update(User currentUser, Note note, Reaction reaction, Emoji emoji);

    void delete(User currentUser, Post post, Reaction reaction);
    void delete(User currentUser, Post post, Comment comment, Reaction reaction);
    void delete(User currentUser, Post post, Comment comment, Reply reply, Reaction reaction);
    void delete(User currentUser, Story story, Reaction reaction);
    void delete(User currentUser, Note note, Reaction reaction);

    List<Reaction> getAll(User currentUser, Post post, Pageable pageable);
    List<Reaction> getAll(User currentUser, Post post, Comment comment, Pageable pageable);
    List<Reaction> getAll(User currentUser, Post post, Comment comment, Reply reply, Pageable pageable);
    List<Reaction> getAll(User currentUser, Story story, Pageable pageable);
    List<Reaction> getAll(User currentUser, Note note, Pageable pageable);

    boolean isAlreadyReactedTo(User currentUser, Post post);
    boolean isAlreadyReactedTo(User currentUser, Post post, Comment comment);
    boolean isAlreadyReactedTo(User currentUser, Post post, Comment comment, Reply reply);
    boolean isAlreadyReactedTo(User currentUser, Story story);
    boolean isAlreadyReactedTo(User currentUser, Note note);

    Reaction getByUserReaction(User currentUser, Post post);
    Reaction getByUserReaction(User currentUser, Post post, Comment comment);
    Reaction getByUserReaction(User currentUser, Post post, Comment comment, Reply reply);
    Reaction getByUserReaction(User currentUser, Story story);
    Reaction getByUserReaction(User currentUser, Note note);

    default List<Reaction> getAllByEmoji(User currentUser, Post post, Emoji emoji, Pageable pageable) {
        return this.getAll(currentUser, post, pageable).stream()
                .filter(react -> react.getEmoji().equals(emoji))
                .toList();
    }

    default List<Reaction> getAllByEmoji(User currentUser, Post post, Comment comment, Emoji emoji, Pageable pageable) {
        return this.getAll(currentUser, post, comment, pageable).stream()
                .filter(react -> react.getEmoji().equals(emoji))
                .toList();
    }

    default List<Reaction> getAllByEmoji(User currentUser, Post post, Comment comment, Reply reply, Emoji emoji, Pageable pageable) {
        return this.getAll(currentUser, post, comment, reply, pageable).stream()
                .filter(react -> react.getEmoji().equals(emoji))
                .toList();
    }

    default List<Reaction> getAllByEmoji(User currentUser, Story story, Emoji emoji, Pageable pageable) {
        return this.getAll(currentUser, story, pageable).stream()
                .filter(react -> react.getEmoji().equals(emoji))
                .toList();
    }

    default List<Reaction> getAllByEmoji(User currentUser, Note note, Emoji emoji, Pageable pageable) {
        return this.getAll(currentUser, note, pageable).stream()
                .filter(react -> react.getEmoji().equals(emoji))
                .toList();
    }
}


