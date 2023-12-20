package com.elleined.forumapi.service.friend;

import com.elleined.forumapi.exception.BlockedException;
import com.elleined.forumapi.exception.NotOwnedException;
import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.friend.FriendRequest;
import com.elleined.forumapi.repository.FriendRequestRepository;
import com.elleined.forumapi.repository.UserRepository;
import com.elleined.forumapi.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final BlockService blockService;


    @Override
    public void acceptFriendRequest(User currentUser, int friendRequestId) {
        FriendRequest friendRequest = friendRequestRepository.findById(friendRequestId).orElseThrow(() -> new ResourceNotFoundException("Friend request with id of " + friendRequestId + " does not exists!"));
        if (!currentUser.getReceiveFriendRequest().contains(friendRequest))
            throw new NotOwnedException("Cannot accept friend request! because you don't have sent this friend request.");

        User requestingUser = friendRequest.getRequestingUser();
        currentUser.getFriends().add(requestingUser);
        requestingUser.getFriends().add(currentUser);

        friendRequestRepository.delete(friendRequest);
        userRepository.save(currentUser);
        userRepository.save(requestingUser);
        log.debug("User with id of {} accepted friend request of user with id of {}", requestingUser.getId(), currentUser.getId());
    }

    @Override
    public void sendFriendRequest(User currentUser, User userToAdd) {
        if (blockService.isBlockedBy(currentUser, userToAdd)) throw new BlockedException("Cannot sent friend request! because you blocked the author of this post with id of !" + userToAdd.getId());
        if (blockService.isYouBeenBlockedBy(currentUser, userToAdd)) throw  new BlockedException("Cannot sent friend request! because this user with id of " + userToAdd.getId() + " already blocked you");

        FriendRequest friendRequest = FriendRequest.builder()
                .createdAt(LocalDateTime.now())
                .requestingUser(currentUser)
                .requestedUser(userToAdd)
                .build();

        currentUser.getSentFriendRequest().add(friendRequest);
        userToAdd.getReceiveFriendRequest().add(friendRequest);

        friendRequestRepository.save(friendRequest);
        userRepository.save(currentUser);
        userRepository.save(userToAdd);
        log.debug("User with id of {} sent a friend request to user with id of {}", currentUser.getId(), userToAdd.getId());
    }

    @Override
    public void unFriend(User currentUser, User userToUnFriend) {
        currentUser.getFriends().remove(userToUnFriend);
        userToUnFriend.getFriends().remove(currentUser);

        userRepository.save(currentUser);
        userRepository.save(userToUnFriend);
        log.debug("User with id of {} unfriended user with id of {}", currentUser.getId(), userToUnFriend.getId());
    }

    @Override
    public Set<User> getAllFriends(User currentUser) {
        return currentUser.getFriends();
    }

    @Override
    public Set<FriendRequest> getAllFriendRequests(User currentUser) {
        return currentUser.getReceiveFriendRequest();
    }
}
