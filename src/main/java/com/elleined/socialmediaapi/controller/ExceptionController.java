package com.elleined.socialmediaapi.controller;

import com.elleined.socialmediaapi.dto.APIResponse;
import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.exception.credential.CredentialException;
import com.elleined.socialmediaapi.exception.field.FieldException;
import com.elleined.socialmediaapi.exception.friend.FriendException;
import com.elleined.socialmediaapi.exception.mention.MentionException;
import com.elleined.socialmediaapi.exception.note.NoteException;
import com.elleined.socialmediaapi.exception.resource.ResourceException;
import com.elleined.socialmediaapi.exception.story.StoryException;
import com.elleined.socialmediaapi.exception.vote.VoteException;
import jakarta.transaction.SystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<APIResponse> handleResourceException(ResourceException ex) {
        var responseMessage = new APIResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            CredentialException.class,
            BlockedException.class
    })
    public ResponseEntity<APIResponse> handleCredentialException(RuntimeException ex) {
        var responseMessage = new APIResponse(HttpStatus.FORBIDDEN, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            FieldException.class,
            FriendException.class,
            MentionException.class,
            NoteException.class,
            StoryException.class,
            VoteException.class,
            SystemException.class
    })
    public ResponseEntity<APIResponse> handleSystemException(RuntimeException ex) {
        var responseMessage = new APIResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<APIResponse>> handleBindException(BindException ex) {
        List<APIResponse> errors = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .map(errorMessage -> new APIResponse(HttpStatus.BAD_REQUEST, errorMessage))
                .toList();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
