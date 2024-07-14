package com.elleined.socialmediaapi.service.friend;

import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.exception.friend.FriendException;
import com.elleined.socialmediaapi.exception.friend.FriendRequestException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.mapper.friend.FriendRequestMapper;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.friend.FriendRequestRepository;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService, FriendServiceRestriction {
    private final UserRepository userRepository;

    private final FriendRequestRepository friendRequestRepository;
    private final FriendRequestMapper friendRequestMapper;

    private final BlockService blockService;

    @Override
    public void acceptFriendRequest(User currentUser, FriendRequest friendRequest) {
        User creator = friendRequest.getCreator();

        if (isFriendsWith(currentUser, creator))
            throw new FriendException("Cannot accept friend request! because you're already friends.");

        if (hasNotReceiveFriendRequest(currentUser, creator))
            throw new ResourceNotOwnedException("Cannot accept friend request! because you don't receive this friend request.");

        currentUser.getFriends().add(creator);
        creator.getFriends().add(currentUser);

        userRepository.save(currentUser);
        userRepository.save(creator);
        log.debug("User with id of {} accepted friend request of user with id of {}", creator.getId(), currentUser.getId());
    }

    @Override
    public void rejectFriendRequest(User currentUser, FriendRequest friendRequest) {
        User creator = friendRequest.getCreator();

        if (hasNotReceiveFriendRequest(currentUser, creator))
            throw new ResourceNotOwnedException("Cannot delete friend request! because you don't have sent this friend request.");

        currentUser.getReceiveFriendRequests().remove(friendRequest);
        creator.getSentFriendRequests().remove(friendRequest);
        friendRequest.setUpdatedAt(LocalDateTime.now());

        userRepository.save(currentUser);
        userRepository.save(creator);
        friendRequestRepository.save(friendRequest);
        log.debug("Current user rejected this friend request");
    }

    @Override
    public FriendRequest sendFriendRequest(User currentUser, User userToAdd) {
        if (hasSendFriendRequest(currentUser, userToAdd))
            throw new FriendRequestException("Cannot sent friend request! because you already sent friend request to this user!");

        if (hasReceiveFriendRequest(currentUser, userToAdd))
            throw new FriendRequestException("Cannot sent friend request! because this user already sent you a friend request!");

        if (isFriendsWith(currentUser, userToAdd))
            throw new FriendException("Cannot sent friend request! because you're already friends.");

        if (isSendingToHimself(currentUser, userToAdd))
            throw new FriendRequestException("Cannot sent friend request! you cannot sent friend request to yourself!");

        if (blockService.isBlockedByYou(currentUser, userToAdd))
            throw new BlockedException("Cannot sent friend request! because you blocked the author of this post with id of !" + userToAdd.getId());

        if (blockService.isYouBeenBlockedBy(currentUser, userToAdd))
            throw  new BlockedException("Cannot sent friend request! because this user with id of " + userToAdd.getId() + " already blocked you");

        FriendRequest friendRequest = friendRequestMapper.toEntity(currentUser, userToAdd);
        currentUser.getSentFriendRequests().add(friendRequest);
        userToAdd.getReceiveFriendRequests().add(friendRequest);

        friendRequestRepository.save(friendRequest);
        userRepository.save(currentUser);
        userRepository.save(userToAdd);
        log.debug("User with id of {} sent a friend request to user with id of {}", currentUser.getId(), userToAdd.getId());
        return friendRequest;
    }

    @Override
    public void unFriend(User currentUser, User userToUnFriend) {
        if (notFriendsWith(currentUser, userToUnFriend))
            throw new FriendException("Cannot unfriend! because you're not friends.");

        currentUser.getFriends().remove(userToUnFriend);
        userToUnFriend.getFriends().remove(currentUser);

        userRepository.save(currentUser);
        userRepository.save(userToUnFriend);
        log.debug("User with id of {} unfriended user with id of {}", currentUser.getId(), userToUnFriend.getId());
    }

    @Override
    public Page<User> getAllFriends(User currentUser, Pageable pageable) {
        return userRepository.findAllFriends(currentUser, pageable).getContent();
    }

    @Override
    public List<FriendRequest> getAllById(List<Integer> ids) {
        return friendRequestRepository.findAllById(ids).stream()
                .sorted(Comparator.comparing(FriendRequest::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public Page<FriendRequest> getAllFriendRequests(User currentUser, Pageable pageable) {
        return userRepository.findAllFriendRequests(currentUser, pageable).getContent();
    }

    @Override
    public FriendRequest save(FriendRequest friendRequest) {
        return friendRequestRepository.save(friendRequest);
    }

    @Override
    public FriendRequest getById(int id) throws ResourceNotFoundException {
        return friendRequestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Friend request with id of " + id + " does not exists!"));
    }

    @Override
    public Page<FriendRequest> getAll(Pageable pageable) {
        return friendRequestRepository.findAll(pageable).getContent();
    }
}
