package com.elleined.forumapi.mapper.react;

import com.elleined.forumapi.dto.ReactionDTO;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.emoji.Emoji;
import com.elleined.forumapi.model.react.CommentReact;

public interface CommentReactionMapper extends ReactionMapper<Comment, CommentReact> {
    @Override
    ReactionDTO toDTO(CommentReact commentReact);

    @Override
    CommentReact toEntity(User currentUser, Comment comment, Emoji emoji);
}
