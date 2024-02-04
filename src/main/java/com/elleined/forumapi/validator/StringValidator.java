package com.elleined.forumapi.validator;

public interface StringValidator {
    default boolean isNotValid(String body) {
        return body == null || body.isEmpty() || body.isBlank();
    }
}
