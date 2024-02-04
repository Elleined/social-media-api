package com.elleined.forumapi.service.comment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.forumapi.mapper.CommentMapper;
import com.elleined.forumapi.repository.CommentRepository;
import com.elleined.forumapi.repository.ReplyRepository;
import com.elleined.forumapi.service.ModalTrackerService;
import com.elleined.forumapi.service.block.BlockService;
import com.elleined.forumapi.service.hashtag.entity.CommentHashTagService;
import com.elleined.forumapi.service.mention.CommentMentionService;
import com.elleined.forumapi.service.pin.PostPinCommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private BlockService blockService;
    @Mock
    private ModalTrackerService modalTrackerService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private ReplyRepository replyRepository;
    @Mock
    private PostPinCommentService postPinCommentService;
    @Mock
    private CommentMentionService commentMentionService;
    @Mock
    private CommentHashTagService commentHashTagService;

    @InjectMocks
    private CommentServiceImpl commentService;


    @Test
    void save() {
        // Expected and Actual Value

        // Mock Data


        // Stubbing methods

        // Calling the method

        // Assertions

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
    void getAllByPost() {
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
    void updateBody() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void getTotalReplies() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }
}