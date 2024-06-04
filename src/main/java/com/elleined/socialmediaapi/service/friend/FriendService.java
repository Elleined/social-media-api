package com.elleined.socialmediaapi.service.friend;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.CustomService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FriendService extends CustomService<FriendRequest> {
    List<User> getAllFriends(User currentUser, Pageable pageable);
    List<FriendRequest> getAllFriendRequests(User currentUser, Pageable pageable);

    // Current user = Requested user
    void acceptFriendRequest(User currentUser, FriendRequest friendRequest);

    // Current user = Requested user
    void rejectFriendRequest(User currentUser, FriendRequest friendRequest);

    FriendRequest sendFriendRequest(User currentUser, User userToAdd);

    void unFriend(User currentUser, User userToUnFriend);
}
