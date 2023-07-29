package com.forum.application.exception;

public class NoLoggedInUserException extends RuntimeException {
    public NoLoggedInUserException(String message) {
        super(message);
    }
}
