package com.elleined.socialmediaapi.validator;


import com.elleined.socialmediaapi.exception.field.MobileNumberException;
import com.elleined.socialmediaapi.utility.StringUtil;

import java.util.List;

public class NumberValidator {

    public void validate(String number) throws MobileNumberException {
        List<Character> letters = StringUtil.toCharArray(number);

        if (letters.stream().anyMatch(Character::isLetter)) throw new MobileNumberException("Phone number cannot contain letters!");
        if (!number.startsWith("09")) throw new MobileNumberException("Phone number must starts with 09!");
        if (number.length() != 11) throw new MobileNumberException("Phone number must be 11 digits long!");
    }
}