package com.elleined.socialmediaapi.validator;


import com.elleined.socialmediaapi.exception.field.PasswordException;
import com.elleined.socialmediaapi.utility.StringUtil;

import java.util.Arrays;
import java.util.List;

public class PasswordValidator {

    public void validate(String password) throws PasswordException {
        List<Character> letters = StringUtil.toCharArray(password);

        if (containsWhiteSpace(password)) throw new PasswordException("Password must not contain space!");
        if (containsAtLeastOneUpperCase(letters)) throw new PasswordException("Password must contain at least 1 upper case!");
        if (containsAtLeastOneLowerCase(letters)) throw new PasswordException("Password must contain at least 1 lower case!");
        if (containsAtLeastOneDigit(letters)) throw new PasswordException("Password must contain at least 1 digit!");
        if (containsAtLeastOneSpecialChar(password)) throw new PasswordException("Password must contain at least 1 special character '@', '#', '$', '_', '/'!");
        if (isNLettersLong(password, 8)) throw new PasswordException("Password must be 8 character long!");
    }

    public boolean isPasswordNotMatch(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }

    public final boolean containsWhiteSpace(String password) {
        return password.contains(" ");
    }

    public final boolean containsAtLeastOneUpperCase(List<Character> letters) {
        return letters.stream().noneMatch(Character::isUpperCase);
    }

    public final boolean containsAtLeastOneLowerCase(List<Character> letters) {
        return letters.stream().noneMatch(Character::isLowerCase);
    }

    public final boolean containsAtLeastOneDigit(List<Character> letters) {
        return letters.stream().noneMatch(Character::isDigit);
    }

    public final boolean containsAtLeastOneSpecialChar(String password) {
        List<Character> specialChars = Arrays.asList('@', '#', '$', '_', '/');
        return specialChars.stream().noneMatch(c -> password.contains(c.toString()));
    }

    public final boolean isNLettersLong(String password, int preferredPasswordLength) {
        return password.length() < preferredPasswordLength;
    }
}