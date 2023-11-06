package com.elleined.forumapi.service.friend;

import com.elleined.forumapi.model.User;

public interface FriendService {
    void addFriend(User currentUser, User userToAdd);
    void unFriend(User currentUser, User userToUnFriend);
}
