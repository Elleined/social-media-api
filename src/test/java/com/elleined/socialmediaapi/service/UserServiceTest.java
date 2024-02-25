package com.elleined.socialmediaapi.service;

import com.elleined.socialmediaapi.dto.UserDTO;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.repository.UserRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BlockService blockService;

    @InjectMocks
    private UserService userService;

    @Test
    void save() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Calling the method
        userService.save(new UserDTO(1, "", "", "", ""));

        // Behavior Verifications
        verify(userRepository).save(any(User.class));

        // Assertions
    }

    @Test
    void getAllById() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userRepository.findAllById(anySet())).thenReturn(new ArrayList<>());

        // Calling the method
        userService.getAllById(new HashSet<>());

        // Behavior Verifications
        verify(userRepository).findAllById(anySet());

        // Assertions
    }

    @Test
    void getByUUID() {
        // Expected Value

        // Mock data
        User expected = new User();

        // Set up method

        // Stubbing methods
        when(userRepository.findByUUID(anyString())).thenReturn(Optional.of(expected));

        // Calling the method
        User actual = userService.getByUUID("UUID");

        // Behavior Verifications
        verify(userRepository).findByUUID(anyString());

        // Assertions
        assertEquals(expected, actual);
    }

    @Test
    void getSuggestedMentions() {
        // Expected Value

        // Mock data
        User currentUser = new User();

        // Set up method

        // Stubbing methods
        when(userRepository.fetchAllByProperty(anyString())).thenReturn(List.of(new User(), new User()));
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);

        // Calling the method
        List<User> actualUsers = userService.getSuggestedMentions(currentUser, "");

        // Behavior Verifications
        verify(userRepository).fetchAllByProperty(anyString());
        verify(blockService, atLeastOnce()).isBlockedBy(any(User.class), any(User.class));
        verify(blockService, atLeastOnce()).isYouBeenBlockedBy(any(User.class), any(User.class));

        // Assertions
        assertFalse(actualUsers.contains(currentUser));
    }
}