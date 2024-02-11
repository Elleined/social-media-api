package com.elleined.forumapi.service.friend;

import com.elleined.forumapi.mapper.FriendRequestMapper;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.friend.FriendRequest;
import com.elleined.forumapi.repository.FriendRequestRepository;
import com.elleined.forumapi.repository.UserRepository;
import com.elleined.forumapi.service.block.BlockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private FriendRequestRepository friendRequestRepository;
    @Mock
    private FriendRequestMapper friendRequestMapper;
    @Mock
    private BlockService blockService;

    @InjectMocks
    private FriendServiceImpl friendService;


    @Test
    void acceptFriendRequest() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);
        currentUser.setFriends(new HashSet<>());

        FriendRequest friendRequest = FriendRequest.builder()
                .requestingUser(User.builder()
                        .friends(new HashSet<>())
                        .build())
                .build();

        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).isFriendsWith(any(User.class));
        doReturn(true).when(currentUser).hasFriendRequest(any(FriendRequest.class));
        doNothing().when(friendRequestRepository).delete(any(FriendRequest.class));
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Calling the method
        // Assertions
        assertDoesNotThrow(() -> friendService.acceptFriendRequest(currentUser, friendRequest));
        assertTrue(currentUser.getFriends().contains(friendRequest.getRequestingUser()));
        assertTrue(friendRequest.getRequestingUser().getFriends().contains(currentUser));

        // Behavior Verifications
        verify(friendRequestRepository).delete(any(FriendRequest.class));
        verify(userRepository, atMost(2)).save(any(User.class));
    }

    @Test
    void deleteFriendRequest() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);
        currentUser.setReceiveFriendRequest(new HashSet<>());

        FriendRequest friendRequest = FriendRequest.builder()
                .id(1)
                .build();

        // Set up method
        currentUser.getReceiveFriendRequest().add(friendRequest);

        // Stubbing methods
        doReturn(true).when(currentUser).hasFriendRequest(any(FriendRequest.class));
        when(userRepository.save(any(User.class))).thenReturn(new User());
        doNothing().when(friendRequestRepository).delete(any(FriendRequest.class));

        // Calling the method
        // Assertions
        assertDoesNotThrow(() -> friendService.deleteFriendRequest(currentUser, friendRequest));
        assertFalse(currentUser.getReceiveFriendRequest().contains(friendRequest));

        // Behavior Verifications
        verify(userRepository).save(any(User.class));
        verify(friendRequestRepository).delete(any(FriendRequest.class));
    }

    @Test
    void sendFriendRequest() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);
        currentUser.setSentFriendRequest(new HashSet<>());

        User userToAdd = spy(User.class);
        userToAdd.setReceiveFriendRequest(new HashSet<>());

        FriendRequest friendRequest = new FriendRequest();

        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).hasAlreadySentFriendRequestTo(any(User.class));
        doReturn(false).when(currentUser).hasAlreadyReceiveFriendRequestTo(any(User.class));
        doReturn(false).when(currentUser).isFriendsWith(any(User.class));
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(friendRequestMapper.toEntity(any(User.class), any(User.class))).thenReturn(friendRequest);
        when(friendRequestRepository.save(any(FriendRequest.class))).thenReturn(friendRequest);
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Calling the method
        // Assertions
        assertDoesNotThrow(() -> friendService.sendFriendRequest(currentUser, userToAdd));
        assertTrue(currentUser.getSentFriendRequest().contains(friendRequest));
        assertTrue(userToAdd.getReceiveFriendRequest().contains(friendRequest));

        // Behavior Verifications
        verify(blockService).isBlockedBy(any(User.class), any(User.class));
        verify(blockService).isYouBeenBlockedBy(any(User.class), any(User.class));
        verify(friendRequestMapper).toEntity(any(User.class), any(User.class));
        verify(friendRequestRepository).save(any(FriendRequest.class));
        verify(userRepository, atMost(2)).save(any(User.class));
    }

    @Test
    void unFriend() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);
        currentUser.setFriends(new HashSet<>());

        User userToUnFriend = spy(User.class);
        userToUnFriend.setFriends(new HashSet<>());

        // Set up method
        currentUser.getFriends().add(userToUnFriend);
        userToUnFriend.getFriends().add(currentUser);

        // Stubbing methods
        doReturn(true).when(currentUser).isFriendsWith(any(User.class));
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Calling the method
        // Assertions
        assertDoesNotThrow(() -> friendService.unFriend(currentUser, userToUnFriend));
        assertFalse(currentUser.getFriends().contains(userToUnFriend));
        assertFalse(userToUnFriend.getFriends().contains(currentUser));

        // Behavior Verifications
        verify(userRepository, atMost(2)).save(any(User.class));
    }

    @Test
    void getAllFriends() {
        // Expected Value

        // Mock data
        User currentUser = User.builder()
                .friends(new HashSet<>())
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        // Assertions
        assertDoesNotThrow(() -> friendService.getAllFriends(currentUser));
        assertEquals(0, currentUser.getFriends().size());

        // Behavior Verifications
    }

    @Test
    void getAllFriendRequests() {
        // Expected Value

        // Mock data
        User currentUser = new User();

        FriendRequest friendRequest1 = FriendRequest.builder()
                .createdAt(LocalDateTime.now().plusMinutes(1))
                .build();

        FriendRequest friendRequest2 = FriendRequest.builder()
                .createdAt(LocalDateTime.now().plusDays(1))
                .build();


        // Set up method
        Set<FriendRequest> rawFriendRequests = Set.of(friendRequest1, friendRequest2);
        currentUser.setReceiveFriendRequest(rawFriendRequests);

        Set<FriendRequest> expectedFriendRequests = Set.of(friendRequest2, friendRequest1);

        // Stubbing methods

        // Calling the method
        // Assertions
        assertIterableEquals(expectedFriendRequests, friendService.getAllFriendRequests(currentUser));

        // Behavior Verifications
    }

    @Test
    void getById() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(friendRequestRepository.findById(anyInt())).thenReturn(Optional.of(new FriendRequest()));

        // Calling the method
        assertDoesNotThrow(() -> friendService.getById(anyInt()));

        // Assertions

        // Behavior Verifications
        verify(friendRequestRepository).findById(anyInt());
    }
}