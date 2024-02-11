package com.elleined.forumapi.service.hashtag;

import com.elleined.forumapi.model.hashtag.HashTag;
import com.elleined.forumapi.repository.HashTagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HashTagServiceImplTest {

    @Mock
    private HashTagRepository hashTagRepository;

    @InjectMocks
    private HashTagServiceImpl hashTagService;

    @Test
    void getAll() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(hashTagRepository.findAll()).thenReturn(new ArrayList<>());

        // Calling the method
        // Assertions
        assertDoesNotThrow(() -> hashTagService.getAll());

        // Behavior Verifications
        verify(hashTagRepository).findAll();
    }

    @Test
    void notExist() {
        // Expected Value

        // Mock data
        HashTag hashTag1 = HashTag.builder()
                .keyword("Keyword1")
                .build();

        HashTag hashTag2 = HashTag.builder()
                .keyword("Keyword2")
                .build();

        // Set up method
        List<HashTag> hashTags = Arrays.asList(hashTag1, hashTag2);

        // Stubbing methods
        when(hashTagRepository.findAll()).thenReturn(hashTags);

        // Calling the method
        // Assertions
        assertTrue(hashTagService.notExist("KeywordToBeSearch"));

        // Behavior Verifications
        verify(hashTagRepository).findAll();
    }

    @Test
    void getByKeyword() {
        // Expected Value
        String keywordToBeSearch = "Keyword1";

        // Mock data
        HashTag expected = HashTag.builder()
                .keyword(keywordToBeSearch)
                .build();

        HashTag hashTag1 = HashTag.builder()
                .keyword("Keyword2")
                .build();

        HashTag hashTag2 = HashTag.builder()
                .keyword("Keyword3")
                .build();

        // Set up method
        List<HashTag> hashTags = Arrays.asList(expected, hashTag1, hashTag2);

        // Stubbing methods
        when(hashTagRepository.findAll()).thenReturn(hashTags);

        // Calling the method
        assertEquals(expected, hashTagService.getByKeyword(keywordToBeSearch));

        // Assertions

        // Behavior Verifications
        verify(hashTagRepository).findAll();
    }

    @Test
    void save() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(hashTagRepository.save(any(HashTag.class))).thenReturn(new HashTag());

        // Calling the method
        // Assertions
        assertDoesNotThrow(() -> hashTagService.save(new HashTag()));

        // Behavior Verifications
        verify(hashTagRepository).save(any(HashTag.class));
    }
}