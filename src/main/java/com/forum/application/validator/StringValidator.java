package com.forum.application.validator;

public interface StringValidator {
    static boolean isNotValidBody(String body) {
        return body == null || body.isEmpty() || body.isBlank();
    }


}
