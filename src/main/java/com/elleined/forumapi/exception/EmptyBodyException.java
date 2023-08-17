package com.elleined.forumapi.exception;

public class EmptyBodyException extends RuntimeException {
    public EmptyBodyException(String message) {
        super(message);
    }
}
