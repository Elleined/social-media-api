package com.elleined.socialmediaapi.validator;


import exception.field.EmailException;

public class EmailValidator {

    public void validate(String email) throws EmailException {
        if (!email.endsWith("@gmail.com")) throw new EmailException("Email must ends with @gmail.com!");
        if (email.startsWith("@")) throw new EmailException("Email should not starts with @!");
    }
}
