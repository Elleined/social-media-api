package com.elleined.socialmediaapi.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class StringValidatorTest {

    @Test
    @DisplayName("Blank")
    void blank() {
        // Expected and Actual Value

        // Mock Data
        StringValidator stringValidator = new Validator();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(stringValidator.isNotValid("   "));

        // Behavior Verifications
    }

    @Test
    @DisplayName("Empty")
    void empty() {
        // Expected and Actual Value

        // Mock Data
        StringValidator stringValidator = new Validator();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(stringValidator.isNotValid(""));

        // Behavior Verifications
    }

    @Test
    @DisplayName("Null value")
    void nullValue() {
        // Expected and Actual Value

        // Mock Data
        StringValidator stringValidator = new Validator();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(stringValidator.isNotValid(null));

        // Behavior Verifications
    }
}