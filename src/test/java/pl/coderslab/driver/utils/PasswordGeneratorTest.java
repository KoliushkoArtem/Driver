package pl.coderslab.driver.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PasswordGeneratorTest {

    @Test
    void generatePasswordPatternMatch() {
        String testPassword = PasswordGenerator.generatePassword();

        assertEquals(12, testPassword.length());
        assertTrue(testPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?!.*\\\\s).{12}$"));
    }
}