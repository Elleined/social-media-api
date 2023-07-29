package com.forum.application.validator;

public interface Validator {
    static boolean isValidBody(String body) {
        return body == null || body.isEmpty() || body.isBlank();
    }


}
