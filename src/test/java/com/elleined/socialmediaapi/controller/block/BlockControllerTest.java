package com.elleined.socialmediaapi.controller.block;

import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BlockController.class)
class BlockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlockService blockService;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;

    @Test
    @DisplayName("Get all users")
    void getAllBlockedUsers() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        when(blockService.getAllBlockedUsers(any(User.class), any(Pageable.class))).thenReturn(List.of(new User()));
        when(userMapper.toDTO(any(User.class))).thenReturn(new UserDTO());

        // Calling the method
        mockMvc.perform(get("/users/{currentUserId}/blocked-users", 1)
                        .param("pageNumber", "1")
                        .param("pageSize", "5")
                        .param("sortDirection", "ASC")
                        .param("sortBy", "id"))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(blockService).getAllBlockedUsers(any(User.class), any(Pageable.class));
        verify(userMapper, atLeastOnce()).toDTO(any(User.class));

        // Assertions
    }

    @Test
    @DisplayName("Is blocked")
    void isBlocked() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        when(blockService.isBlockedByYou(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);

        // Calling the method
        mockMvc.perform(get("/users/{currentUserId}/is-blocked/{otherUserId}", 1, 2))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService, times(2)).getById(anyInt());
        verify(blockService).isBlockedByYou(any(User.class), any(User.class));
        verify(blockService).isYouBeenBlockedBy(any(User.class), any(User.class));

        // Assertions
    }

    @Test
    @DisplayName("Block user")
    void blockUser() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        doNothing().when(blockService).blockUser(any(User.class), any(User.class));

        // Calling the method
        mockMvc.perform(patch("/users/{currentUserId}/block/{userToBeBlockedId}", 1, 2))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService, times(2)).getById(anyInt());
        verify(blockService).blockUser(any(User.class), any(User.class));

        // Assertions
    }

    @Test
    @DisplayName("Unblock user")
    void unblockUser() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        doNothing().when(blockService).unBlockUser(any(User.class), any(User.class));

        // Calling the method
        mockMvc.perform(patch("/users/{currentUserId}/unblock/{userToBeUnblockedId}", 1, 2))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService, times(2)).getById(anyInt());
        verify(blockService).unBlockUser(any(User.class), any(User.class));

        // Assertions
    }
}