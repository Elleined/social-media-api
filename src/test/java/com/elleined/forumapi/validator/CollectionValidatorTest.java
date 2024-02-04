package com.elleined.forumapi.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class CollectionValidatorTest {

    @Test
    void isValid() {
        // Expected and Actual Value

        // Mock Data
        Set<String> setString = new HashSet<>();
        setString.add("Value 1");

        List<String> listString = new ArrayList<>();
        listString.add("Value 1");
        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(CollectionValidator.isValid(setString));
        assertTrue(CollectionValidator.isValid(listString));

        // Behavior Verifications
    }

    @Test
    void isNotValid() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(CollectionValidator.isNotValid(new HashSet<>()));
        assertTrue(CollectionValidator.isNotValid(new HashSet<>()));

        // Behavior Verifications
    }
}