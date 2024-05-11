package com.elleined.socialmediaapi.service.note;

import com.elleined.socialmediaapi.mapper.note.NoteMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.repository.note.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;
    @Mock
    private NoteMapper noteMapper;

    @InjectMocks
    private NoteServiceImpl noteService;


    @Test
    void save() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);

        Note expected = new Note();

        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).hasNote();
        when(noteMapper.toEntity(any(User.class), anyString())).thenReturn(expected);
        when(noteRepository.save(any(Note.class))).thenReturn(expected);

        // Calling the method
        // Assertions
        Note actual = noteService.save(currentUser, "Thought");
        assertNotNull(actual);
        assertEquals(expected, actual);

        // Behavior Verifications
        verify(noteMapper).toEntity(any(User.class), anyString());
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void update() {
        // Expected Value
        String expectedThought = "New thought";

        // Mock data
        User currentUser = spy(User.class);
        Note note = Note.builder()
                .thought("Old Thought")
                .build();

        // Set up method
        currentUser.setNote(note);

        // Stubbing methods
        doReturn(true).when(currentUser).hasNote();
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        // Calling the method
        Note actual = noteService.update(currentUser, expectedThought);

        // Assertions
        assertEquals(expectedThought, actual.getThought());

        // Behavior Verifications
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void delete() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);
        Note note = new Note();

        // Set up method
        currentUser.setNote(note);

        // Stubbing methods
        doReturn(true).when(currentUser).hasNote();
        doAnswer(e -> {
            currentUser.setNote(null);
            return note;
        }).when(noteRepository).delete(any(Note.class));

        // Calling the method
        noteService.delete(currentUser);

        // Assertions
        assertNull(currentUser.getNote());

        // Behavior Verifications
        verify(noteRepository).delete(any(Note.class));
    }

    @Test
    void deleteExpiredNotes() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(noteRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(noteRepository).deleteAll(anyList());

        // Calling the method
        noteService.deleteExpiredNotes();

        // Assertions

        // Behavior Verifications
        verify(noteRepository).findAll();
        verify(noteRepository).deleteAll(anyList());
    }
}