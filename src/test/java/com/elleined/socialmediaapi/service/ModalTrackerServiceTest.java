package com.elleined.socialmediaapi.service;

import com.elleined.socialmediaapi.model.ModalTracker;
import com.elleined.socialmediaapi.repository.ModalTrackerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModalTrackerServiceTest {

    @Mock
    private ModalTrackerRepository modalTrackerRepository;

    @InjectMocks
    private ModalTrackerService modalTrackerService;

    @Test
    void saveTrackerOfUserById() {
        // Expected Value

        // Mock data
        ModalTracker modalTracker = new ModalTracker();

        // Set up method

        // Stubbing methods
        when(modalTrackerRepository.save(any(ModalTracker.class))).thenReturn(modalTracker);

        // Calling the method
        modalTrackerService.saveTrackerByUserId(1, 1, ModalTracker.Type.REPLY);

        // Behavior Verifications
        verify(modalTrackerRepository).save(any(ModalTracker.class));

        // Assertions
    }

    @Test
    void deleteTrackerOfUserById() {
        // Expected Value
        ModalTracker.Type type = ModalTracker.Type.REPLY;

        // Mock data
        ModalTracker modalTracker = ModalTracker.builder()
                .type(type)
                .build();

        // Set up method

        // Stubbing methods
        when(modalTrackerRepository.findById(anyInt())).thenReturn(Optional.of(modalTracker));
        doNothing().when(modalTrackerRepository).deleteById(anyInt());

        // Calling the method
        modalTrackerService.deleteTrackerOfUserById(1, type);

        // Behavior Verifications
        verify(modalTrackerRepository).findById(anyInt());
        verify(modalTrackerRepository).deleteById(anyInt());

        // Assertions
    }

    @Test
    void isModalOpen() {
        // Expected Value
        int receiverId = 1;
        ModalTracker.Type type = ModalTracker.Type.REPLY;
        int associatedTypeIdOpened = 1;

        // Mock data
        ModalTracker modalTracker = ModalTracker.builder()
                .receiverId(receiverId)
                .type(type)
                .associatedTypeIdOpened(associatedTypeIdOpened)
                .build();

        // Set up method

        // Stubbing methods
        when(modalTrackerRepository.findById(anyInt())).thenReturn(Optional.of(modalTracker));

        // Calling the method
        assertTrue(modalTrackerService.isModalOpen(receiverId, associatedTypeIdOpened, type));

        // Behavior Verifications
        verify(modalTrackerRepository).findById(anyInt());

        // Assertions
    }

    @Test
    void deleteAll() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        doNothing().when(modalTrackerRepository).deleteAll();

        // Calling the method
        modalTrackerService.deleteAll();

        // Behavior Verifications
        verify(modalTrackerRepository).deleteAll();

        // Assertions
    }
}