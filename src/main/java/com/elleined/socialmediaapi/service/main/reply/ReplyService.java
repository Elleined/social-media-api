package com.elleined.socialmediaapi.service.main.reply;

import com.elleined.socialmediaapi.exception.*;
import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.CustomService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ReplyService extends CustomService<Reply> {
    Reply save(User currentUser,
               Comment comment,
               String body,
               MultipartFile attachedPicture,
               Set<User> mentionedUsers,
               Set<String> keywords) throws CommentSectionException,
            ResourceNotOwnedException,
            BlockedException, IOException;

    void delete(User currentUser, Comment comment, Reply reply) throws ResourceNotOwnedException;

    Reply update(User currentUser, Reply reply, String newBody, String newAttachedPicture)
            throws ResourceNotFoundException,
            ResourceNotOwnedException;

    List<Reply> getAllByComment(User currentUser, Comment comment) throws ResourceNotFoundException;
}
