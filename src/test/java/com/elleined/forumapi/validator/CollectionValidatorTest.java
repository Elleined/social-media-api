package com.elleined.forumapi.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CollectionValidatorTest {

    @Test
    void isValid() {
        // Expected and Actual Value
        CollectionValidator collectionValidator = new Validator();

        // Mock Data
        Set<String> setString = new HashSet<>();
        setString.add("Value 1");

        List<String> listString = new ArrayList<>();
        listString.add("Value 1");
        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(collectionValidator.isValid(setString));
        assertTrue(collectionValidator.isValid(listString));

        // Behavior Verifications
    }

    @Test
    void isNotValid() {
        // Expected and Actual Value
        CollectionValidator collectionValidator = new Validator();

        // Mock Data

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(collectionValidator.isNotValid(new HashSet<>()));
        assertTrue(collectionValidator.isNotValid(new HashSet<>()));

        // Behavior Verifications
    }
}