package com.elleined.forumapi.service.friend;

import com.elleined.forumapi.exception.*;
import com.elleined.forumapi.mapper.FriendRequestMapper;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.friend.FriendRequest;
import com.elleined.forumapi.repository.FriendRequestRepository;
import com.elleined.forumapi.repository.UserRepository;
import com.elleined.forumapi.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;

    private final FriendRequestRepository friendRequestRepository;
    private final FriendRequestMapper friendRequestMapper;

    private final BlockService blockService;

    @Override
    public void acceptFriendRequest(User currentUser, FriendRequest friendRequest) {
        User requestingUser = friendRequest.getRequestingUser();

        if (currentUser.isFriendsWith(requestingUser))
            throw new FriendException("Cannot accept friend request! because you're already friends.");
        if (!currentUser.hasFriendRequest(friendRequest))
            throw new NotOwnedException("Cannot accept friend request! because you don't receive this friend request.");

        currentUser.getFriends().add(requestingUser);
        requestingUser.getFriends().add(currentUser);

        friendRequestRepository.delete(friendRequest);
        userRepository.save(currentUser);
        userRepository.save(requestingUser);
        log.debug("User with id of {} accepted friend request of user with id of {}", requestingUser.getId(), currentUser.getId());
    }

    @Override
    public void deleteFriendRequest(User currentUser, FriendRequest friendRequest) {
        if (!currentUser.hasFriendRequest(friendRequest))
            throw new NotOwnedException("Cannot delete friend request! because you don't have sent this friend request.");

        int friendRequestId = friendRequest.getId();
        currentUser.getReceiveFriendRequest().remove(friendRequest);

        userRepository.save(currentUser);
        friendRequestRepository.delete(friendRequest);
        log.debug("User with id of {} delete friend request with id of {}", currentUser.getId(), friendRequestId);
    }

    @Override
    public void sendFriendRequest(User currentUser, User userToAdd) {
        if (currentUser.hasAlreadySentFriendRequestTo(userToAdd))
            throw new FriendRequestException("Cannot sent friend request! becuase you already sent friend request to this user!");
        if (currentUser.hasAlreadyReceiveFriendRequestTo(userToAdd))
            throw new FriendRequestException("Cannot sent friend request! because this user already sent you a friend request!");
        if (currentUser.isFriendsWith(userToAdd))
            throw new FriendException("Cannot sent friend request! because you're already friends.");
        if (blockService.isBlockedBy(currentUser, userToAdd))
            throw new BlockedException("Cannot sent friend request! because you blocked the author of this post with id of !" + userToAdd.getId());
        if (blockService.isYouBeenBlockedBy(currentUser, userToAdd))
            throw  new BlockedException("Cannot sent friend request! because this user with id of " + userToAdd.getId() + " already blocked you");

        FriendRequest friendRequest = friendRequestMapper.toEntity(currentUser, userToAdd);
        currentUser.getSentFriendRequest().add(friendRequest);
        userToAdd.getReceiveFriendRequest().add(friendRequest);

        friendRequestRepository.save(friendRequest);
        userRepository.save(currentUser);
        userRepository.save(userToAdd);
        log.debug("User with id of {} sent a friend request to user with id of {}", currentUser.getId(), userToAdd.getId());
    }

    @Override
    public void unFriend(User currentUser, User userToUnFriend) {
        if (!currentUser.isFriendsWith(userToUnFriend))
            throw new FriendException("Cannot accept friend request! because you're not friends.");
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
    public List<FriendRequest> getAllFriendRequests(User currentUser) {
        return currentUser.getReceiveFriendRequest().stream()
                .sorted(Comparator.comparing(FriendRequest::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public FriendRequest getById(int id) throws ResourceNotFoundException {
        return friendRequestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Friend request with id of " + id + " does not exists!"));
    }
}
