package com.elleined.socialmediaapi.service.friend;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.CustomService;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface FriendService extends CustomService<FriendRequest> {
    Set<User> getAllFriends(User currentUser, Pageable pageable);
    List<FriendRequest> getAllFriendRequests(User currentUser, Pageable pageable);

    void acceptFriendRequest(User currentUser, FriendRequest friendRequest);
    void rejectFriendRequest(User currentUser, FriendRequest friendRequest);

    void sendFriendRequest(User currentUser, User userToAdd);

    void unFriend(User currentUser, User userToUnFriend);
}
