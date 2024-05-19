package com.elleined.socialmediaapi.validator;


import com.elleined.socialmediaapi.exception.field.PasswordException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PasswordValidator {
    private final static int preferredPasswordLength = 8;

    public void validate(String password) throws PasswordException {
        List<Character> letters = FieldValidator.toCharArray(password);

        if (containsWhiteSpace(password)) throw new PasswordException("Password must not contain space!");
        if (containsAtLeastOneUpperCase(letters)) throw new PasswordException("Password must contain at least 1 upper case!");
        if (containsAtLeastOneLowerCase(letters)) throw new PasswordException("Password must contain at least 1 lower case!");
        if (containsAtLeastOneDigit(letters)) throw new PasswordException("Password must contain at least 1 digit!");
        if (containsAtLeastOneSpecialChar(password)) throw new PasswordException("Password must contain at least 1 special character '@', '#', '$', '_', '/'!");
        if (isNLettersLong(password)) throw new PasswordException("Password must be 8 character long!");
    }

    private boolean isPasswordNotMatch(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }

    private boolean containsWhiteSpace(String password) {
        return password.contains(" ");
    }

    private boolean containsAtLeastOneUpperCase(List<Character> letters) {
        return letters.stream().noneMatch(Character::isUpperCase);
    }

    private boolean containsAtLeastOneLowerCase(List<Character> letters) {
        return letters.stream().noneMatch(Character::isLowerCase);
    }

    private boolean containsAtLeastOneDigit(List<Character> letters) {
        return letters.stream().noneMatch(Character::isDigit);
    }

    private boolean containsAtLeastOneSpecialChar(String password) {
        List<Character> specialChars = Arrays.asList('@', '#', '$', '_', '/');
        return specialChars.stream().noneMatch(c -> password.contains(c.toString()));
    }

    private boolean isNLettersLong(String password) {
        return password.length() < preferredPasswordLength;
    }
}