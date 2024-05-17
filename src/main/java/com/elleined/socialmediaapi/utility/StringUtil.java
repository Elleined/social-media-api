package com.elleined.socialmediaapi.utility;

import java.util.ArrayList;
import java.util.List;

public interface StringUtil {
    static List<Character> toCharArray(String field) {
        List<Character> characters = new ArrayList<>();
        for (char letter : field.toCharArray()) {
            characters.add(letter);
        }
        return characters;
    }
}