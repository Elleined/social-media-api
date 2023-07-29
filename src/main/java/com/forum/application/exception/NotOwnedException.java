package com.forum.application.exception;

public class NotOwnedException extends RuntimeException {

    public NotOwnedException(String message) {
        super(message);
    }
}
