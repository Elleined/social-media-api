package com.elleined.socialmediaapi.service.reaction;

import com.elleined.socialmediaapi.model.react.Reaction;
import com.elleined.socialmediaapi.model.user.User;

public interface ReactionServiceRestriction {

    default boolean ownedBy(Reaction reaction, User currentUser) {
        return reaction.getCreator().equals(currentUser);
    }

    default boolean notOwnedBy(Reaction reaction, User currentUser) {
        return !this.ownedBy(reaction, currentUser);
    }
}
