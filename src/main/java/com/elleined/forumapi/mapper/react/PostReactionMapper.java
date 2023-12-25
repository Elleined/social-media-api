package com.elleined.forumapi.mapper.react;

import com.elleined.forumapi.dto.ReactionDTO;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.emoji.Emoji;
import com.elleined.forumapi.model.react.PostReact;

public interface PostReactionMapper extends ReactionMapper<Post, PostReact> {
    @Override
    ReactionDTO toDTO(PostReact postReact);

    @Override
    PostReact toEntity(User currentUser, Post post, Emoji emoji);
}
