package com.elleined.forumapi.service.hashtag.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.forumapi.mapper.hashtag.HashTagMapper;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.hashtag.HashTag;
import com.elleined.forumapi.repository.HashTagRepository;
import com.elleined.forumapi.repository.PostRepository;
import com.elleined.forumapi.service.hashtag.HashTagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class PostHashTagServiceTest {

    @Mock
    private HashTagRepository hashTagRepository;
    @Mock
    private HashTagService hashTagService;
    @Mock
    private HashTagMapper hashTagMapper;
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostHashTagService postHashTagService;

    @Test
    void getAllByHashTagKeyword() {
        // Expected Value

        // Mock data
        User currentUser = new User();
        Post userOwnedPost = Post.builder()
                .author(currentUser)
                .build();

        Post otherUserPost = Post.builder()
                .author(new User())
                .build();

        // Set up method
        Set<Post> rawPosts = Set.of(userOwnedPost, otherUserPost);
        Set<Post> expectedPosts = Set.of(otherUserPost);

        // Stubbing methods
        when(hashTagRepository.getAllPostByHashTagKeyword(anyString())).thenReturn(rawPosts);

        // Calling the method

        // Assertions
        assertIterableEquals(expectedPosts, postHashTagService.getAllByHashTagKeyword(currentUser, "Keyword"));

        // Behavior Verifications
        verify(hashTagRepository).getAllPostByHashTagKeyword(anyString());
    }

    @Test
    void saveIfNotExists() {
        // Expected Value

        // Mock data
        Post comment = new Post();
        comment.setHashTags(new HashSet<>());

        HashTag expectedHashtag = new HashTag();
        expectedHashtag.setPosts(new HashSet<>());

        // Set up method

        // Stubbing methods
        when(hashTagService.notExist(anyString())).thenReturn(true);
        when(hashTagMapper.toEntity(anyString())).thenReturn(expectedHashtag);
        when(hashTagService.save(any(HashTag.class))).thenReturn(expectedHashtag);
        when(postRepository.save(any(Post.class))).thenReturn(comment);

        // Calling the method
        // Assertions
        assertEquals(expectedHashtag, postHashTagService.save(comment, "Keyword"));
        assertTrue(comment.getHashTags().contains(expectedHashtag));
        assertTrue(expectedHashtag.getPosts().contains(comment));

        // Behavior Verifications
        verify(hashTagService).notExist(anyString());
        verify(hashTagMapper).toEntity(anyString());
        verify(hashTagService).save(any(HashTag.class));
        verify(postRepository).save(any(Post.class));

        assertDoesNotThrow(() -> postHashTagService.save(comment, "Keyword"));
    }

    @Test
    void saveIfExisting() {
        // Expected Value

        // Mock data
        Post comment = new Post();
        comment.setHashTags(new HashSet<>());

        HashTag expectedHashTag = new HashTag();
        expectedHashTag.setPosts(new HashSet<>());

        // Set up method

        // Stubbing methods
        when(hashTagService.notExist(anyString())).thenReturn(false);
        when(hashTagService.getByKeyword(anyString())).thenReturn(expectedHashTag);
        when(hashTagService.save(any(HashTag.class))).thenReturn(expectedHashTag);
        when(postRepository.save(any(Post.class))).thenReturn(comment);

        // Calling the method
        // Assertions
        assertEquals(expectedHashTag, postHashTagService.save(comment, "Keyword"));
        assertTrue(comment.getHashTags().contains(expectedHashTag));
        assertTrue(expectedHashTag.getPosts().contains(comment));

        // Behavior Verifications
        verify(hashTagService).notExist(anyString());
        verify(hashTagService).getByKeyword(anyString());
        verify(hashTagService).save(any(HashTag.class));
        verify(postRepository).save(any(Post.class));

        assertDoesNotThrow(() -> postHashTagService.save(comment, "Keyword"));
    }

}