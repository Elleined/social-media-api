package com.elleined.socialmediaapi.service.main.reply;

import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.reaction.Reaction;

public interface ReplyRestrictionService {

    default boolean notOwned(Reply reply, Reaction reaction) {
        return !reply.getReactions().contains(reaction);
    }
}
