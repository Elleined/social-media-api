package com.elleined.socialmediaapi.validator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class FieldValidator {
    public final boolean isValid(String field) {
        return field != null && !field.isBlank() && !field.isEmpty();
    }

    public final boolean isNotValid(String field) {
        return field == null || field.isEmpty() || field.isBlank();
    }

    public final boolean isNotValid(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public final boolean isNotValid(Object object) {
        return object == null;
    }

    public final boolean containsDigit(String field) {
        return FieldValidator.toCharArray( field ).stream().anyMatch(Character::isDigit);
    }

    public final boolean containsLetter(String phoneNumber) {
        return FieldValidator.toCharArray( phoneNumber ).stream().anyMatch(Character::isLetter);
    }

    public static List<Character> toCharArray(String field) {
        List<Character> characters = new ArrayList<>();
        for (char letter : field.toCharArray()) {
            characters.add(letter);
        }
        return characters;
    }
}
