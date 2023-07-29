package com.forum.application.exception;

public class EmptyBodyException extends RuntimeException {
    public EmptyBodyException(String message) {
        super(message);
    }
}
