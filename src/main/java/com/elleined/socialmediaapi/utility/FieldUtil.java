package com.elleined.socialmediaapi.utility;

public interface FieldUtil {

    static boolean isValid(String field) {
        return field != null && !field.isBlank() && !field.isEmpty();
    }

    static boolean isNotValid(String field) {
        return field == null || field.isEmpty() || field.isBlank();
    }

    static boolean isNotValid(Object object) {
        return object == null;
    }
}
