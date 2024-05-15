package com.elleined.socialmediaapi.validator;

import java.util.ArrayList;

public class FieldValidator {

    public final boolean containsDigit(String field) {
        return getPassCharArray( field ).stream().anyMatch(Character::isDigit);
    }

    public final boolean containsLetter(String phoneNumber) {
        return getPassCharArray( phoneNumber ).stream().anyMatch(Character::isLetter);
    }

    private ArrayList<Character> getPassCharArray(String field) {
        ArrayList<Character> passChars = new ArrayList<>();
        for (Character character : field.toCharArray()) {
            passChars.add(character);
        }
        return passChars;
    }
}
