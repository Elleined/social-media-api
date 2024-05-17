package com.elleined.socialmediaapi.service.main.comment;

import com.elleined.socialmediaapi.exception.*;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
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

    Comment getById(int commentId) throws ResourceNotFoundException;
    List<Comment> getAllById(Set<Integer> ids);
    List<Comment> getAllByPost(User currentUser, Post post) throws ResourceNotFoundException;

    void delete(User currentUser, Post post, Comment comment);

    Comment update(User currentUser, Post post, Comment comment, String newBody, String newAttachedPicture)
            throws ResourceNotFoundException,
            NotOwnedException;
}
