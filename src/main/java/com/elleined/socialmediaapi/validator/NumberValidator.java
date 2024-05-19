package com.elleined.socialmediaapi.validator;


import com.elleined.socialmediaapi.exception.field.MobileNumberException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NumberValidator {

    public void validate(String number) throws MobileNumberException {
        List<Character> letters = FieldValidator.toCharArray(number);

        if (letters.stream().anyMatch(Character::isLetter)) throw new MobileNumberException("Phone number cannot contain letters!");
        if (!number.startsWith("09")) throw new MobileNumberException("Phone number must starts with 09!");
        if (number.length() != 11) throw new MobileNumberException("Phone number must be 11 digits long!");
    }
}