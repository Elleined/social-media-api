package com.elleined.forumapi.mapper.react;

import com.elleined.forumapi.dto.ReactionDTO;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.emoji.Emoji;
import com.elleined.forumapi.model.react.ReplyReact;

public interface ReplyReactionMapper extends ReactionMapper<Reply, ReplyReact> {
    @Override
    ReactionDTO toDTO(ReplyReact replyReact);

    @Override
    ReplyReact toEntity(User currentUser, Reply reply, Emoji emoji);
}
