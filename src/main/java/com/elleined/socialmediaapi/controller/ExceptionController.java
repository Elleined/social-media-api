package com.elleined.socialmediaapi.controller;

import com.elleined.socialmediaapi.dto.ResponseMessage;
import com.elleined.socialmediaapi.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleNotFoundException(ResourceNotFoundException ex) {
        var responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            BlockedException.class,
            NotOwnedException.class,
            EmptyBodyException.class,
            ClosedCommentSectionException.class,
            UpvoteException.class,
            MentionException.class,
            FriendException.class,
            FriendRequestException.class,
            NoteException.class
    })
    public ResponseEntity<ResponseMessage> handleBadRequestExceptions(RuntimeException ex) {
        var responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ResponseMessage>> handleBindException(BindException ex) {
        List<ResponseMessage> errors = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .map(errorMessage -> new ResponseMessage(HttpStatus.BAD_REQUEST, errorMessage))
                .toList();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
