package com.elleined.socialmediaapi.service.comment;

import com.elleined.socialmediaapi.exception.*;
import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CommentService {
    Comment save(User currentUser,
                 Post post,
                 String body,
                 MultipartFile attachedPicture,
                 Set<User> mentionedUsers,
                 Set<String> keywords)
            throws ResourceNotFoundException,
            ClosedCommentSectionException,
            BlockedException,
            EmptyBodyException,
            IOException;

    void delete(User currentUser, Post post, Comment comment);

    List<Comment> getAllByPost(User currentUser, Post post) throws ResourceNotFoundException;

    Comment getById(int commentId) throws ResourceNotFoundException;

    Comment updateBody(User currentUser, Post post, Comment comment, String newBody)
            throws ResourceNotFoundException,
            NotOwnedException;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    int getTotalReplies(Comment comment);

    public boolean isAlreadyUpvoted(Comment comment) {
        return this.getVotedComments().stream().anyMatch(comment::equals);
    }
}
