package com.elleined.socialmediaapi.controller.user;

import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.request.user.UserRequest;
import com.elleined.socialmediaapi.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;

    @Test
    void save() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data
        UserRequest userRequest = UserRequest.builder()
                .name("Name")
                .email("email@gmail.com")
                .picture("Picture")
                .build();

        // Set up method

        // Stubbing methods
        when(userService.save(any(UserRequest.class))).thenReturn(new User());
        when(userMapper.toDTO(any(User.class))).thenReturn(new UserDTO());

        // Calling the method
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).save(any(UserRequest.class));
        verify(userMapper).toDTO(any(User.class));

        // Assertions
    }

    @Test
    void getById() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        when(userMapper.toDTO(any(User.class))).thenReturn(new UserDTO());

        // Calling the method
        mockMvc.perform(get("/users/{id}", 1))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(userMapper).toDTO(any(User.class));

        // Assertions
    }

    @Test
    void getAllById() throws Exception {
        // Pre defined values
        List<Integer> ids = List.of(1, 2);
        // Expected Value

        // Mock data

        // Set up method
        String json = objectMapper.writeValueAsString(ids);

        // Stubbing methods
        when(userService.getAllById(anyList())).thenReturn(List.of(new User()));
        when(userMapper.toDTO(any(User.class))).thenReturn(new UserDTO());

        // Calling the method
        mockMvc.perform(get("/users/get-all-by-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getAllById(anyList());
        verify(userMapper, atLeastOnce()).toDTO(any(User.class));

        // Assertions
    }

    @Test
    void getByUUID() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getByUUID(anyString())).thenReturn(new User());
        when(userMapper.toDTO(any(User.class))).thenReturn(new UserDTO());

        // Calling the method
        mockMvc.perform(get("/users/uuid/{uuid}", "UUID"))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getByUUID(anyString());
        verify(userMapper).toDTO(any(User.class));

        // Assertions
    }

    @Test
    void getAllSuggestedMentions() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        when(userService.getAllSuggestedMentions(any(User.class), anyString(), any(Pageable.class))).thenReturn(List.of(new User()));
        when(userMapper.toDTO(any(User.class))).thenReturn(new UserDTO());

        // Calling the method
        mockMvc.perform(get("/users/{currentUserId}/mention/suggested-users", 1)
                        .param("name", "Name")
                        .param("pageNumber", "1")
                        .param("pageSize", "5")
                        .param("sortDirection", "ASC")
                        .param("sortBy", "id"))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(userService).getAllSuggestedMentions(any(User.class), anyString(), any(Pageable.class));
        verify(userMapper, atLeastOnce()).toDTO(any(User.class));

        // Assertions
    }
}