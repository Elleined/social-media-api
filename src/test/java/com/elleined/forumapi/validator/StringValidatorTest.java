package com.elleined.forumapi.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StringValidatorTest {

    @Test
    @DisplayName("Blank")
    void blank() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(StringValidator.isNotValidBody("   "));

        // Behavior Verifications
    }

    @Test
    @DisplayName("Empty")
    void empty() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(StringValidator.isNotValidBody(""));

        // Behavior Verifications
    }

    @Test
    @DisplayName("Null value")
    void nullValue() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(StringValidator.isNotValidBody(null));

        // Behavior Verifications
    }
}