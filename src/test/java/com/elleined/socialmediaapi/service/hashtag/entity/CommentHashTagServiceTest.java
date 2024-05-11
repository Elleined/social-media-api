package com.elleined.socialmediaapi.service.hashtag.entity;

import com.elleined.socialmediaapi.mapper.hashtag.HashTagMapper;
import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.repository.main.CommentRepository;
import com.elleined.socialmediaapi.repository.hashtag.HashTagRepository;
import com.elleined.socialmediaapi.service.hashtag.HashTagService;
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
class CommentHashTagServiceTest {

    @Mock
    private HashTagRepository hashTagRepository;
    @Mock
    private HashTagService hashTagService;
    @Mock
    private HashTagMapper hashTagMapper;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentHashTagService commentHashTagService;

    @Test
    void getAllByHashTagKeyword() {
        // Expected Value

        // Mock data
        User currentUser = new User();
        Comment userOwnedComment = Comment.builder()
                .commenter(currentUser)
                .build();

        Comment otherUserComment = Comment.builder()
                .commenter(new User())
                .build();

        // Set up method
        Set<Comment> rawComments = Set.of(userOwnedComment, otherUserComment);
        Set<Comment> expectedComments = Set.of(otherUserComment);

        // Stubbing methods
        when(hashTagRepository.getAllCommentByHashTagKeyword(anyString())).thenReturn(rawComments);

        // Calling the method

        // Assertions
        assertIterableEquals(expectedComments, commentHashTagService.getAllByHashTagKeyword(currentUser, "Keyword"));

        // Behavior Verifications
        verify(hashTagRepository).getAllCommentByHashTagKeyword(anyString());
    }

    @Test
    void saveIfNotExists() {
        // Expected Value

        // Mock data
        Comment comment = new Comment();
        comment.setHashTags(new HashSet<>());

        HashTag expectedHashtag = new HashTag();
        expectedHashtag.setComments(new HashSet<>());

        // Set up method

        // Stubbing methods
        when(hashTagService.notExist(anyString())).thenReturn(true);
        when(hashTagMapper.toEntity(anyString())).thenReturn(expectedHashtag);
        when(hashTagService.save(any(HashTag.class))).thenReturn(expectedHashtag);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // Calling the method
        // Assertions
        assertEquals(expectedHashtag, commentHashTagService.save(comment, "Keyword"));
        assertTrue(comment.getHashTags().contains(expectedHashtag));
        assertTrue(expectedHashtag.getComments().contains(comment));

        // Behavior Verifications
        verify(hashTagService).notExist(anyString());
        verify(hashTagMapper).toEntity(anyString());
        verify(hashTagService).save(any(HashTag.class));
        verify(commentRepository).save(any(Comment.class));

        assertDoesNotThrow(() -> commentHashTagService.save(comment, "Keyword"));
    }

    @Test
    void saveIfExisting() {
        // Expected Value

        // Mock data
        Comment comment = new Comment();
        comment.setHashTags(new HashSet<>());

        HashTag expectedHashTag = new HashTag();
        expectedHashTag.setComments(new HashSet<>());

        // Set up method

        // Stubbing methods
        when(hashTagService.notExist(anyString())).thenReturn(false);
        when(hashTagService.getByKeyword(anyString())).thenReturn(expectedHashTag);
        when(hashTagService.save(any(HashTag.class))).thenReturn(expectedHashTag);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // Calling the method
        // Assertions
        assertEquals(expectedHashTag, commentHashTagService.save(comment, "Keyword"));
        assertTrue(comment.getHashTags().contains(expectedHashTag));
        assertTrue(expectedHashTag.getComments().contains(comment));

        // Behavior Verifications
        verify(hashTagService).notExist(anyString());
        verify(hashTagService).getByKeyword(anyString());
        verify(hashTagService).save(any(HashTag.class));
        verify(commentRepository).save(any(Comment.class));

        assertDoesNotThrow(() -> commentHashTagService.save(comment, "Keyword"));
    }
}