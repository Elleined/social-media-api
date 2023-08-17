package com.elleined.forumapi.exception;

public class NotOwnedException extends RuntimeException {

    public NotOwnedException(String message) {
        super(message);
    }
}
