package com.elleined.forumapi.service.reply;

import com.elleined.forumapi.exception.*;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ReplyService {
    Reply save(User currentUser,
               Comment comment,
               String body,
               MultipartFile attachedPicture,
               Set<User> mentionedUsers,
               Set<String> keywords) throws EmptyBodyException,
            ClosedCommentSectionException,
            ResourceNotFoundException,
            BlockedException, IOException;

    void delete(User currentUser, Comment comment, Reply reply) throws NotOwnedException;

    Reply updateBody(User currentUser, Reply reply, String newReplyBody)
            throws ResourceNotFoundException,
            NotOwnedException;

    List<Reply> getAllByComment(User currentUser, Comment comment) throws ResourceNotFoundException;

    Reply getById(int replyId) throws ResourceNotFoundException;
}
