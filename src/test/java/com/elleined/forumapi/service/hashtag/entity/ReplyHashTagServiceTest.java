package com.elleined.forumapi.service.hashtag.entity;

import com.elleined.forumapi.mapper.hashtag.HashTagMapper;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.hashtag.HashTag;
import com.elleined.forumapi.repository.HashTagRepository;
import com.elleined.forumapi.repository.ReplyRepository;
import com.elleined.forumapi.service.hashtag.HashTagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReplyHashTagServiceTest {

    @Mock
    private HashTagRepository hashTagRepository;
    @Mock
    private HashTagService hashTagService;
    @Mock
    private HashTagMapper hashTagMapper;
    @Mock
    private ReplyRepository replyRepository;

    @InjectMocks
    private ReplyHashTagService replyHashTagService;

    @Test
    void getAllByHashTagKeyword() {
        // Expected Value

        // Mock data
        User currentUser = new User();
        Reply userOwnedReply = Reply.builder()
                .replier(currentUser)
                .build();

        Reply otherUserReply = Reply.builder()
                .replier(new User())
                .build();

        // Set up method
        Set<Reply> rawReplies = Set.of(userOwnedReply, otherUserReply);
        Set<Reply> expectedReplies = Set.of(otherUserReply);

        // Stubbing methods
        when(hashTagRepository.getAllReplyByHashTagKeyword(anyString())).thenReturn(rawReplies);

        // Calling the method

        // Assertions
        assertIterableEquals(expectedReplies, replyHashTagService.getAllByHashTagKeyword(currentUser, "Keyword"));

        // Behavior Verifications
        verify(hashTagRepository).getAllReplyByHashTagKeyword(anyString());
    }

    @Test
    void saveIfNotExists() {
        // Expected Value

        // Mock data
        Reply comment = new Reply();
        comment.setHashTags(new HashSet<>());

        HashTag expectedHashtag = new HashTag();
        expectedHashtag.setReplies(new HashSet<>());

        // Set up method

        // Stubbing methods
        when(hashTagService.notExist(anyString())).thenReturn(true);
        when(hashTagMapper.toEntity(anyString())).thenReturn(expectedHashtag);
        when(hashTagService.save(any(HashTag.class))).thenReturn(expectedHashtag);
        when(replyRepository.save(any(Reply.class))).thenReturn(comment);

        // Calling the method
        // Assertions
        assertEquals(expectedHashtag, replyHashTagService.save(comment, "Keyword"));
        assertTrue(comment.getHashTags().contains(expectedHashtag));
        assertTrue(expectedHashtag.getReplies().contains(comment));

        // Behavior Verifications
        verify(hashTagService).notExist(anyString());
        verify(hashTagMapper).toEntity(anyString());
        verify(hashTagService).save(any(HashTag.class));
        verify(replyRepository).save(any(Reply.class));

        assertDoesNotThrow(() -> replyHashTagService.save(comment, "Keyword"));
    }

    @Test
    void saveIfExisting() {
        // Expected Value

        // Mock data
        Reply comment = new Reply();
        comment.setHashTags(new HashSet<>());

        HashTag expectedHashTag = new HashTag();
        expectedHashTag.setReplies(new HashSet<>());

        // Set up method

        // Stubbing methods
        when(hashTagService.notExist(anyString())).thenReturn(false);
        when(hashTagService.getByKeyword(anyString())).thenReturn(expectedHashTag);
        when(hashTagService.save(any(HashTag.class))).thenReturn(expectedHashTag);
        when(replyRepository.save(any(Reply.class))).thenReturn(comment);

        // Calling the method
        // Assertions
        assertEquals(expectedHashTag, replyHashTagService.save(comment, "Keyword"));
        assertTrue(comment.getHashTags().contains(expectedHashTag));
        assertTrue(expectedHashTag.getReplies().contains(comment));

        // Behavior Verifications
        verify(hashTagService).notExist(anyString());
        verify(hashTagService).getByKeyword(anyString());
        verify(hashTagService).save(any(HashTag.class));
        verify(replyRepository).save(any(Reply.class));

        assertDoesNotThrow(() -> replyHashTagService.save(comment, "Keyword"));
    }
}