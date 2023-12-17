package com.elleined.forumapi.service.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.repository.*;
import com.elleined.forumapi.service.ModalTrackerService;
import com.elleined.forumapi.service.block.BlockService;
import com.elleined.forumapi.service.image.ImageUploader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BlockService blockService;

    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ImageUploader imageUploader;

    @Mock
    private ModalTrackerService modalTrackerService;

    @Mock
    private MentionRepository mentionRepository;

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void save() {
        // Expected and Actual Value

        // Mock Data
        User user = new User();
        // Stubbing methods
        
        // Calling the method
        // Assertions
        assertDoesNotThrow(() -> postService.save(any(User.class), anyString(), any(MultipartFile.class), anySet()));

        // Behavior Verifications
    }

    @Test
    void delete() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void updateBody() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void updateCommentSectionStatus() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void getById() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void getAll() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void getPinnedComment() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void getTotalCommentsAndReplies() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void pin() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void unpin() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void mention() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void mentionAll() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void like() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void unLike() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void isLiked() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void savedPost() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void unSavedPost() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void getAllSavedPosts() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void sharePost() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void unSharePost() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void getAllSharedPosts() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }
}