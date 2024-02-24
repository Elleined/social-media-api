package com.elleined.socialmediaapi.exception;

public class NotOwnedException extends RuntimeException {

    public NotOwnedException(String message) {
        super(message);
    }
}
